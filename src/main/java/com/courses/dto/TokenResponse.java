package com.courses.dto;

public record TokenResponse(

        String token,
        long expiresIn
) {}