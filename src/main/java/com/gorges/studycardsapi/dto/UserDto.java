package com.gorges.studycardsapi.dto;

import com.gorges.studycardsapi.models.UserEntity;
import com.gorges.studycardsapi.utils.enums.Roles;

import lombok.Data;

public class UserDto {
    @Data
    public static class User {
        private String username;
        private String email;
        private Roles role;
    }

    @Data
    public static class Register {
        private String username;
        private String email;
        private String password;
        private String confirmPassword;

        public Register() {
        }

        public Register(String username, String email, String password, String confirmPassword) {
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
}
