package com.courses.dto;

import jakarta.validation.constraints.*;


public record LessonCreateRequest(
        @NotBlank(message = "name is required")
        @Size(min = 3, message = "name must have at least 3 characters")
        String name,

        @NotBlank(message = "email is required")
        @Email(message = "email must be valid")
        String email,

        @NotBlank(message = "password is required")
        @Size(min = 8, message ="password must have as least 8 characters")
        String password
) {}