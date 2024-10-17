package com.gorges.studycardsapi.models;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.gorges.studycardsapi.utils.enums.Roles;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "users")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false, unique = true)
    private String email;

    @JsonIgnore
    private String password;

    private Roles role;

    @Column(nullable = false)
    private boolean isEnabled;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @Column(nullable = true, name = "deleteAt")
    private LocalDateTime deleteAt;

    private LocalDateTime lastLogin;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CardCollectionHistoryEntity> cardCollectionHistory;

    @ManyToMany
    @JoinTable(name = "user_favorite_card_collections", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "card_collection_id"))
    private List<CardCollectionEntity> favoritedCardCollections;

    @OneToMany(mappedBy = "owner")
    private List<CardCollectionEntity> ownedCardCollections;

    public void addToFavoritedCards(CardCollectionEntity card) {
        if (!favoritedCardCollections.contains(card)) {
            favoritedCardCollections.add(card);
        }
    }

    public void removeFromFavoritedCards(CardCollectionEntity card) {
        favoritedCardCollections.remove(card);
    }

    public List<CardCollectionEntity> getOwnedCards() {
        return ownedCardCollections;
    }

    public void addToCardCollectionHistory(CardCollectionHistoryEntity history) {
        cardCollectionHistory.add(history);
        history.setUser(this);
    }

    public void removeFromCardCollectionHistory(CardCollectionHistoryEntity history) {
        cardCollectionHistory.remove(history);
        history.setUser(null);
    }
}