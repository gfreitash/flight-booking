package com.gfreitash.flight_booking.dto.output;

import jakarta.validation.constraints.NotBlank;

public record AuthenticationResponse(@NotBlank String token) {
}
