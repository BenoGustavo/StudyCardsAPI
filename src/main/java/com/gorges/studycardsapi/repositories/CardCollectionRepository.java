package com.gorges.studycardsapi.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gorges.studycardsapi.models.CardCollectionEntity;

public interface CardCollectionRepository extends JpaRepository<CardCollectionEntity, UUID> {

}
