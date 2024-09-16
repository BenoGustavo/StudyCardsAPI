package com.gorges.studycardsapi.models;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.gorges.studycardsapi.utils.enums.Roles;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
    private String password;
    private Roles role;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;
    @UpdateTimestamp
    private LocalDateTime updatedAt;
    @Column(nullable = true, name = "deleteAt")
    private LocalDateTime deleteAt;
    private LocalDateTime lastLogin;

    // public UserDto toDto() {
    // UserDto userDto = new UserDto();
    // userDto.setId(this.id);
    // userDto.setUsername(this.username);
    // userDto.setEmail(this.email);
    // userDto.setRole(this.role);
    // userDto.setCreatedAt(this.createdAt);
    // userDto.setUpdatedAt(this.updatedAt);
    // userDto.setDeleteAt(this.deleteAt);
    // userDto.setLastLogin(this.lastLogin);
    // return userDto;
    // }
}
