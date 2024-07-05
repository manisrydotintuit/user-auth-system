package com.manibhadra.user_auth_system.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AuthResponse {
    private String token;
    private boolean success;
    private LocalDateTime timestamp;
    private int statusCode;
    private String message;
    private String errorMessage;

    // Constructor
    public AuthResponse(String token, boolean success, int statusCode, String message, String errorMessage) {
        this.token = token;
        this.success = success;
        this.timestamp = LocalDateTime.now();
        this.statusCode = statusCode;
        this.message = message;
        this.errorMessage = errorMessage;
    }

    // Getters and setters
    // Omitted for brevity
}