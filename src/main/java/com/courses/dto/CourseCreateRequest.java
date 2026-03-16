package com.courses.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CourseCreateRequest(
        @NotBlank(message = "name is required")
        @Size(min = 3, message = "NAME MUST HAVE AT LEAST 3 CHARACTERS")
        String name
) {
}