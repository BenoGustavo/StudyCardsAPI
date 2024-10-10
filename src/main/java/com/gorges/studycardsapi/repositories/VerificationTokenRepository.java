package com.gorges.studycardsapi.repositories;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gorges.studycardsapi.models.VerificationTokenEntity;
import com.gorges.studycardsapi.utils.enums.Token;

public interface VerificationTokenRepository extends JpaRepository<VerificationTokenEntity, UUID> {
    Optional<VerificationTokenEntity> findByToken(String token);

    Optional<VerificationTokenEntity> findByTokenAndTokenType(String token, Token tokenType);

    Optional<List<VerificationTokenEntity>> findByUserIdAndTokenType(UUID userId, Token tokenType);
}
