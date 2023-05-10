package com.gfreitash.flight_booking.services.dto.validations.input.role;

import com.gfreitash.flight_booking.services.dto.input.RoleInputDTO;
import com.gfreitash.flight_booking.services.validations.Validates;
import com.gfreitash.flight_booking.services.validations.FieldsConstraintsAreValid;
import jakarta.validation.Validator;

@Validates(RoleInputDTO.class)
public class ValidateRoleInputDtoConstraintsAreValid extends FieldsConstraintsAreValid<RoleInputDTO> {

    public ValidateRoleInputDtoConstraintsAreValid(Validator validator) {
        super(validator);
    }
}
