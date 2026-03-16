package com.courses.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CourseUpdateRequest(

        @NotBlank(message = "name is required")
        @Size(min = 3, message = "name must have at least 3 characters")
        String name

) {}