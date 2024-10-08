package com.gorges.studycardsapi.services;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.gorges.studycardsapi.dto.AuthenticationDtos.LoginDto;
import com.gorges.studycardsapi.dto.UserDto.Register;
import com.gorges.studycardsapi.error.http.BadRequest400Exception;
import com.gorges.studycardsapi.error.http.Unauthorized401Exception;
import com.gorges.studycardsapi.models.UserEntity;
import com.gorges.studycardsapi.models.VerificationTokenEntity;
import com.gorges.studycardsapi.repositories.UserRepository;
import com.gorges.studycardsapi.utils.enums.Roles;
import com.gorges.studycardsapi.utils.enums.Token;

@Service
public class AuthenticationService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private VerificationTokenService tokenService;
    @Autowired
    private EmailService emailService;

    public String signup(@RequestBody Register Register) throws IllegalArgumentException {
        UserEntity user = userRepository.findByEmail(Register.getEmail()).orElse(null);
        boolean isEmailInUse = user == null;
        boolean isAccountActivated = user != null && user.isEnabled();

        if (isEmailInUse && isAccountActivated) {
            throw new BadRequest400Exception("Email already in use");
        }

        if (!isAccountActivated && user != null) {
            userRepository.deleteAllUserTokens(user);
            userRepository.delete(user);
        }

        UserEntity userEntity = Register.toEntity();
        userEntity.setRole(Roles.ROLE_USER);
        userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));

        userEntity.setEnabled(false);

        UserEntity newUser = userRepository.save(userEntity);

        emailService.sendVerificationEmail(newUser,
                tokenService.createVerificationToken(newUser, Token.ACCOUNT_VALIDATION).getToken());

        return "A email has been sent to your email address. Please verify your email address to login";
    }

    public void sendRecoverPasswordToken(String email) {
        UserEntity user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        tokenService.sendRecoverPasswordToken(user);
    }

    public void recoverPassword(String token, String newPassword) {
        VerificationTokenEntity verificationToken = tokenService.findByToken(token, Token.PASSWORD_RECOVERY);

        if (verificationToken.getExpiryDate().isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("Token expired");
        }

        UserEntity user = verificationToken.getUser();
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
    }

    public UserEntity authenticate(LoginDto loginDto) throws AuthenticationException, UsernameNotFoundException {
        if (isUserAuthenticated()) {
            throw new Unauthorized401Exception("Logout first before authenticating");
        }

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginDto.getEmail(),
                            loginDto.getPassword()));
        } catch (AuthenticationException e) {
            throw new Unauthorized401Exception("Invalid email or password");
        }

        UserEntity user = userRepository.findByEmail(loginDto.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        if (!user.isEnabled()) {
            throw new Unauthorized401Exception("Account not verified, check your email adress or signup");
        }

        if (user.getDeleteAt() != null) {
            throw new Unauthorized401Exception("Deleted user");
        }

        userRepository.updateLastLogin(user.getId());
        return user;
    }

    private boolean isUserAuthenticated() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getPrincipal() != "anonymousUser";
    }
}
