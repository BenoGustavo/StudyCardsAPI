package com.gorges.studycardsapi.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.gorges.studycardsapi.dto.UserDto.Register;
import com.gorges.studycardsapi.error.http.BadRequest400Exception;
import com.gorges.studycardsapi.error.http.NotFound404Exception;
import com.gorges.studycardsapi.error.http.Unauthorized401Exception;
import com.gorges.studycardsapi.error.http.UserAlreadyDeletedException;
import com.gorges.studycardsapi.models.UserEntity;
import com.gorges.studycardsapi.repositories.UserRepository;
import com.gorges.studycardsapi.utils.enums.Roles;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    PasswordEncoder passwordEncoder;

    public UserEntity create(Register user, Roles role) {
        if (role == null) {
            throw new BadRequest400Exception("Role is required as request parameter");
        }

        UserEntity userEntity = user.toEntity();
        userEntity.setRole(role);
        userEntity.setPassword(passwordEncoder.encode(user.getPassword()));
        userEntity.setEnabled(true);

        return userEntity;
    }

    // Get active users
    public UserEntity getActiveById(UUID id) {
        return userRepository.findByIdActive(id).orElseThrow(() -> new NotFound404Exception("Usuário não encontrado"));
    }

    public List<UserEntity> getActiveOnly() {
        return userRepository.findAllActive();
    }

    // Get all
    public List<UserEntity> getAll() {
        return userRepository.findAll();
    }

    public UserEntity delete(UUID id) {
        if (!isUserOwner(id)) {
            throw new Unauthorized401Exception("You are not the owner of this user");
        }

        UserEntity user = userRepository.findById(id).orElseThrow();
        if (user.getDeleteAt() != null) {
            throw new UserAlreadyDeletedException("User already deleted");
        }

        user.setDeleteAt(LocalDateTime.now());
        return userRepository.save(user);
    }

    private boolean isUserOwner(UUID id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        String idEmail = userRepository.findById(id)
                .orElseThrow(() -> new NotFound404Exception("User " + email + " not found"))
                .getEmail();

        return email.equals(idEmail);
    }

}
