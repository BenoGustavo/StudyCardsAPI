package com.gorges.studycardsapi.services;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import com.gorges.studycardsapi.dto.UserDto.Register;
import com.gorges.studycardsapi.error.http.BadRequest400Exception;
import com.gorges.studycardsapi.models.UserEntity;
import com.gorges.studycardsapi.models.VerificationTokenEntity;
import com.gorges.studycardsapi.repositories.VerificationTokenRepository;
import com.gorges.studycardsapi.utils.enums.Roles;

@Service
public class VerificationTokenService {

    // Should be configured in application.properties
    @Value("${verification.token.expiry.hours}")
    private int tokenExpiryHours;

    @Autowired
    @Lazy
    private UserService userService;

    @Autowired
    EmailService emailService;

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

    public void resendVerificationEmail(UserEntity user) {
        VerificationTokenEntity token = createVerificationToken(user);
        emailService.sendVerificationEmail(user, token.getToken());
    }

    public boolean isTokenExpired(VerificationTokenEntity token) {
        return token.getExpiryDate().isBefore(LocalDateTime.now());
    }

    public VerificationTokenEntity findByToken(String token) {
        Optional<VerificationTokenEntity> verificationToken = tokenRepository.findByToken(token);

        if (!verificationToken.isPresent()) {
            throw new BadRequest400Exception("Invalid verification token");
        }

        VerificationTokenEntity tokenEntity = verificationToken.get();

        if (isTokenExpired(tokenEntity)) {
            throw new BadRequest400Exception("Verification token has expired");
        }

        UserEntity user = tokenEntity.getUser();
        user.setEnabled(true);

        Register newUserRegister = new Register(user.getUsername(), user.getEmail(), user.getPassword(),
                user.getPassword());

        userService.create(newUserRegister, Roles.ROLE_USER);

        return tokenEntity;
    }
}