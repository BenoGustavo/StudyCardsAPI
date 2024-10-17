package com.gorges.studycardsapi.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gorges.studycardsapi.dto.CardDto.CardCollection;
import com.gorges.studycardsapi.error.http.BadRequest400Exception;
import com.gorges.studycardsapi.error.http.NotFound404Exception;
import com.gorges.studycardsapi.jwt.JwtUtil;
import com.gorges.studycardsapi.models.CardCollectionEntity;
import com.gorges.studycardsapi.models.UserEntity;
import com.gorges.studycardsapi.repositories.CardCollectionRepository;
import com.gorges.studycardsapi.repositories.UserRepository;

@Service
public class CardCollectionService {
    @Autowired
    private CardCollectionRepository cardCollectionRepository;
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private UserRepository userRepository;

    public CardCollectionEntity create(CardCollection cardCollectionDTO) {
        String currentUserEmail = jwtUtil.getCurrentUserEmail();

        if (cardCollectionDTO.getSubject() == null || cardCollectionDTO.getSubject().isEmpty()) {
            throw new BadRequest400Exception("Subject is required");
        }

        UserEntity cardCollectionOwner = userRepository.findByEmail(currentUserEmail).orElseThrow(
                () -> new NotFound404Exception("User not found"));

        // Check if the user already has a collection with the same subject
        List<CardCollectionEntity> cardCollections = cardCollectionRepository
                .findByOwnerId(cardCollectionOwner.getId());

        for (CardCollectionEntity cardCollection : cardCollections) {
            if (cardCollection.getSubject().equals(cardCollectionDTO.getSubject())) {
                throw new BadRequest400Exception("You already have a collection with this subject");
            }
        }

        CardCollectionEntity cardCollection = new CardCollectionEntity();

        cardCollection.setSubject(cardCollectionDTO.getSubject());
        cardCollection.setDescription(cardCollectionDTO.getDescription());
        cardCollection.setOwner(cardCollectionOwner);

        return cardCollectionRepository.save(cardCollection);
    }
}
