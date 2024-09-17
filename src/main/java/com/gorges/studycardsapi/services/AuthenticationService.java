package com.gorges.studycardsapi.services;

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
import com.gorges.studycardsapi.error.http.Unauthorized401Exception;
import com.gorges.studycardsapi.models.UserEntity;
import com.gorges.studycardsapi.repositories.UserRepository;
import com.gorges.studycardsapi.utils.enums.Roles;

@Service
public class AuthenticationService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    VerificationTokenService tokenService;
    @Autowired
    EmailService emailService;

    public String signup(@RequestBody Register Register) throws IllegalArgumentException {
        UserEntity userEntity = Register.toEntity();
        userEntity.setRole(Roles.ROLE_USER);
        userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));

        userEntity.setEnabled(false);
        UserEntity newUser = userRepository.save(userEntity);

        emailService.sendVerificationEmail(newUser, tokenService.createVerificationToken(newUser).getToken());

        return "A email has been sent to your email address. Please verify your email address to login";
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
