package com.gfreitash.flight_booking.dto;
import com.gfreitash.flight_booking.entities.Airport;
import jakarta.validation.constraints.NotNull;
import org.springframework.hateoas.server.core.Relation;

@Relation(collectionRelation = "airports", itemRelation = "airport")
public record AirportResource(@NotNull Integer id,
                              @NotNull String name,
                              @NotNull String iataCode,
                              @NotNull String city,
                              @NotNull String state,
                              @NotNull String stateAbbreviation,
                              @NotNull Double latitude,
                              @NotNull Double longitude) {

    public AirportResource(Airport airport) {
        this(
                airport.getId(),
                airport.getName(),
                airport.getIataCode(),
                airport.getCity(),
                airport.getState(),
                airport.getStateAbbreviation(),
                airport.getLatitude(),
                airport.getLongitude()
        );
    }
}
