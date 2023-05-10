package com.gfreitash.flight_booking.services.dto.validations.input.role;


import com.gfreitash.flight_booking.exceptions.RoleCanNotBeOwnSubRoleException;
import com.gfreitash.flight_booking.repositories.RoleRepository;
import com.gfreitash.flight_booking.services.dto.input.RoleInputDTO;
import com.gfreitash.flight_booking.services.validations.SpecificationValidator;
import com.gfreitash.flight_booking.services.validations.Validates;
import lombok.RequiredArgsConstructor;

@Validates(RoleInputDTO.class)
@RequiredArgsConstructor
public class ValidateRoleNotSubroleOfItself implements SpecificationValidator<RoleInputDTO> {

    private final RoleRepository roleRepository;

    @Override
    public void validate(RoleInputDTO dto) {
        if (dto.parentRole() == null) {
            return;
        }
        var parentRole = roleRepository.findByName(dto.parentRole()).orElse(null);
        while (parentRole != null) {
            if (parentRole.getName().equals(dto.name())) {
                throw new RoleCanNotBeOwnSubRoleException("Role cannot be a subrole of itself");
            }
            parentRole = parentRole.getParentRole();
        }
    }
}
