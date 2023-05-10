package com.gfreitash.flight_booking.services.dto.validations.input.role;

import com.gfreitash.flight_booking.services.dto.input.RoleInputDTO;
import com.gfreitash.flight_booking.services.validations.SpecificationValidator;
import com.gfreitash.flight_booking.services.validations.Validates;

import java.text.Collator;

@Validates(RoleInputDTO.class)
public class ValidateParentRoleIsNotSameRole implements SpecificationValidator<RoleInputDTO> {
    @Override
    public void validate(RoleInputDTO dto) {
        var collator = Collator.getInstance();
        collator.setStrength(Collator.PRIMARY);

        if (dto.name() != null && dto.parentRole() != null && collator.compare(dto.name(), dto.parentRole()) == 0) {
            throw new IllegalArgumentException("Parent role cannot be the same as the role");
        }
    }
}
