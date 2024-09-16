package com.gorges.studycardsapi.error.http;

public class UserAlreadyDeletedException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    private static final int ERROR_CODE = 409;

    public UserAlreadyDeletedException(String message) {
        super(message);
    }

    public int getCode() {
        return ERROR_CODE;
    }
}