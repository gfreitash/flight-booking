package com.gfreitash.flight_booking.dto.output;

import com.gfreitash.flight_booking.entities.Role;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import org.springframework.hateoas.server.core.Relation;

@Builder
@Relation(collectionRelation = "roles", itemRelation = "role")
public record RoleOutputDTO(
        @NotNull Integer id,

        @NotBlank String name,

        String parentRole
) {


    public RoleOutputDTO(Role role) {
        this(
                role.getId(),
                role.getName(),
                role.getParentRole() != null ? role.getParentRole().getName() : null
        );
    }
}
