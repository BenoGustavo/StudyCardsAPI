package com.gorges.studycardsapi.dto;

import lombok.Data;

@Data
public class RecoverPasswordRequest {
    private String token;
    private String password;
}