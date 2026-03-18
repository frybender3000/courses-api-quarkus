package com.courses.dto;

import jakarta.validation.constraints.NotBlank;

public record LessonCreateRequest(
        @NotBlank(message = "name is required")
        String name
) {}