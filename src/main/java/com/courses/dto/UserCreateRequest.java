package com.courses.dto;

import jakarta.validation.constraints.*;

public record UserCreateRequest(

        @NotBlank(message = "name is required")
        String name,

        @NotBlank(message = "email is required")
        @Email(message = "email must be valid")
        String email,

        @NotBlank(message = "password is required")
        @Size(min = 8, message = "password must have at least 8 characters")
        String password








) {}