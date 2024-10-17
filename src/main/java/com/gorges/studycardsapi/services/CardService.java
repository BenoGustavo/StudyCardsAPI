package com.gorges.studycardsapi.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gorges.studycardsapi.dto.CardDto.Card;
import com.gorges.studycardsapi.error.http.BadRequest400Exception;
import com.gorges.studycardsapi.error.http.NotFound404Exception;
import com.gorges.studycardsapi.jwt.JwtUtil;
import com.gorges.studycardsapi.models.CardEntity;
import com.gorges.studycardsapi.models.UserEntity;
import com.gorges.studycardsapi.repositories.CardCollectionRepository;
import com.gorges.studycardsapi.repositories.CardRepository;
import com.gorges.studycardsapi.repositories.UserRepository;

@Service
public class CardService {
    @Autowired
    private CardRepository cardRepository;

    @Autowired
    private CardCollectionRepository cardCollectionRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtil jwtUtil;

    public CardEntity create(Card cardDTO) {
        String currentUserEmail = jwtUtil.getCurrentUserEmail();

        // Validate if card already exists in the collection
        List<CardEntity> cards = cardRepository.findByCollectionIdAndDeletedAtIsNull(cardDTO.getCollection());
        cards.forEach(card -> {
            if (card.getQuestion().equals(cardDTO.getQuestion())) {
                throw new BadRequest400Exception("This question already exists in this collection");
            }
        });

        UserEntity cardOwner = userRepository.findByEmail(currentUserEmail)
                .orElseThrow(() -> new NotFound404Exception("User not found"));

        // Creates a new card
        CardEntity newCard = new CardEntity();

        newCard.setQuestion(cardDTO.getQuestion());
        newCard.setAnswer(cardDTO.getAnswer());

        // Uses the cardCollectionRepository to find the card collection by id sent by
        // the DTO
        newCard.setCollection(cardCollectionRepository.findById(cardDTO.getCollection()).orElseThrow(
                () -> new NotFound404Exception("Card collection not found, perhaps it was deleted by the owner")));

        newCard.setPublic(cardDTO.isPublic());
        newCard.setCardAnswerType(cardDTO.getCardAnswerType());
        newCard.setOwner(cardOwner);

        return cardRepository.save(newCard);
    }

    public CardEntity update(UUID id, Card cardDTO) {
        String currentUserEmail = jwtUtil.getCurrentUserEmail();
        CardEntity card = cardRepository.findById(id).orElseThrow(() -> new NotFound404Exception("Card not found"));

        // Validate if the user is the owner of the card
        if (!card.getOwner().getId().equals(userRepository.findByEmail(currentUserEmail).get().getId())) {
            throw new BadRequest400Exception("You are not the owner of this card");
        }

        // Validate if card already exists in the collection
        List<CardEntity> cards = cardRepository.findByCollectionIdAndDeletedAtIsNull(cardDTO.getCollection());
        cards.forEach(c -> {
            if (c.getQuestion().equals(cardDTO.getQuestion()) && !c.getId().equals(id)) {
                throw new BadRequest400Exception("This question already exists in this collection");
            }
        });

        // Update only the fields that are provided (non-null)
        if (cardDTO.getQuestion() != null) {
            card.setQuestion(cardDTO.getQuestion());
        }

        if (cardDTO.getAnswer() != null) {
            card.setAnswer(cardDTO.getAnswer());
        }

        if (cardDTO.getCardAnswerType() != null) {
            card.setCardAnswerType(cardDTO.getCardAnswerType());
        }

        card.setPublic(cardDTO.isPublic());

        return cardRepository.save(card);
    }

    public CardEntity delete(UUID id) {
        String currentUserEmail = jwtUtil.getCurrentUserEmail();
        CardEntity card = cardRepository.findById(id).orElseThrow(() -> new NotFound404Exception("Card not found"));

        // Validate if the user is the owner of the card
        if (!card.getOwner().getEmail().equals(currentUserEmail)) {
            throw new BadRequest400Exception("You are not the owner of this card");
        }

        card.setDeletedAt(LocalDateTime.now());

        cardRepository.save(card);
        return card;
    }

    // Getters
    public CardEntity getById(UUID id) {
        return cardRepository.findByIdAndIsPublicTrueAndDeletedAtIsNull(id)
                .orElseThrow(() -> new NotFound404Exception("Card not found"));
    }

    public List<CardEntity> getByOwnerId(UUID id) {
        return cardRepository.findByOwnerIdAndIsPublicTrueAndDeletedAtIsNull(id);
    }

    public List<CardEntity> getMyCards() {
        UUID currentUserId = userRepository.findByEmail(jwtUtil.getCurrentUserEmail()).orElseThrow(
                () -> new NotFound404Exception("User not found")).getId();
        return cardRepository.findByOwnerIdAndDeletedAtIsNull(currentUserId);
    }

    // Admin only method services

    public List<CardEntity> admGetAll() {
        // Should be acessed only by admin, because it returns all cards even the
        // private ones
        return cardRepository.findAll();
    };

    public CardEntity admGetById(UUID id) {
        // Should be acessed only by admin, because it returns all cards even the
        // private ones
        return cardRepository.findById(id).orElseThrow(() -> new NotFound404Exception("Card not found"));
    }
}
