package com.gfreitash.flight_booking.dto.input;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record RoleInputDTO(
        @NotBlank String name,
        String parentRole
) {
}
