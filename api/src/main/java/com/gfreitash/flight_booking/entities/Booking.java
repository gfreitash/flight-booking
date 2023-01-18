package com.gfreitash.flight_booking.entities;

import jakarta.persistence.Embeddable;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Booking {
    @EmbeddedId
    private BookingId id;

    @Embeddable
    @Getter
    @Setter
    public static class BookingId implements Serializable {
        @NotNull
        private Integer flightId;
        @NotNull
        private Integer passengerId;
    }
}
