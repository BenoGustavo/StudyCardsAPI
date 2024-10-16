package com.gorges.studycardsapi.repositories;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gorges.studycardsapi.models.CardEntity;

public interface CardRepository extends JpaRepository<CardEntity, UUID> {
    List<CardEntity> findByIsPublicTrueAndDeletedAtIsNull();

    Optional<CardEntity> findByIdAndIsPublicTrueAndDeletedAtIsNull(UUID id);

    List<CardEntity> findByCardCollectionIdAndDeletedAtIsNull(UUID cardCollectionId);

    List<CardEntity> findByOwnerIdAndIsPublicTrueAndDeletedAtIsNull(UUID ownerId);

    List<CardEntity> findByOwnerIdAndDeletedAtIsNull(UUID ownerId);
}
