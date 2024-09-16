package com.gorges.studycardsapi.dto;

import com.gorges.studycardsapi.models.UserEntity;

import lombok.Data;

public class AuthenticationDtos {
    @Data
    public class RegisterDto {
        private String username;
        private String email;
        private String password;
        private String confirmPassword;

        public RegisterDto(String username, String email, String password, String confirmPassword) {
            this.username = username;
            this.email = email;
            this.password = password;
            this.confirmPassword = confirmPassword;
        }

        public UserEntity toEntity() {
            UserEntity userEntity = new UserEntity();
            userEntity.setUsername(this.username);
            userEntity.setEmail(this.email);
            userEntity.setPassword(this.password);
            return userEntity;
        }
    }

    @Data
    public class LoginDto {
        private String email;
        private String password;
    }

}
