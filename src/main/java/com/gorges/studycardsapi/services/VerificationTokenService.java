package com.gorges.studycardsapi.services;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.gorges.studycardsapi.models.UserEntity;
import com.gorges.studycardsapi.models.VerificationTokenEntity;
import com.gorges.studycardsapi.repositories.VerificationTokenRepository;

@Service
public class VerificationTokenService {

    // Should be configured in application.properties
    @Value("${verification.token.expiry.hours}")
    private int tokenExpiryHours;

    @Autowired
    private VerificationTokenRepository tokenRepository;

    public VerificationTokenEntity createVerificationToken(UserEntity user) {
        VerificationTokenEntity token = new VerificationTokenEntity();
        token.setUser(user);
        token.setToken(UUID.randomUUID().toString());
        token.setExpiryDate(LocalDateTime.now().plusHours(tokenExpiryHours));
        tokenRepository.save(token);
        return token;
    }

    public boolean isTokenExpired(VerificationTokenEntity token) {
        return token.getExpiryDate().isBefore(LocalDateTime.now());
    }

    public Optional<VerificationTokenEntity> findByToken(String token) {
        return tokenRepository.findByToken(token);
    }
}