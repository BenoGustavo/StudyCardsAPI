package com.gorges.studycardsapi.repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gorges.studycardsapi.models.CardCollectionEntity;

public interface CardCollectionRepository extends JpaRepository<CardCollectionEntity, UUID> {
    CardCollectionEntity findBySubject(String subject);

    List<CardCollectionEntity> findByOwnerId(UUID ownerId);
}
