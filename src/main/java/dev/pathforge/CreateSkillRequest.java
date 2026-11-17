package dev.pathforge;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

public record CreateSkillRequest(@NotBlank String name,
    @Positive int estimatedHours
) {


}
