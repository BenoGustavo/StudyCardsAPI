package com.gorges.studycardsapi.dto;

import com.gorges.studycardsapi.utils.enums.Roles;

import lombok.Data;

@Data
public class UserDto {
    private String username;
    private String email;
    private Roles role;
}
