package com.gfreitash.flight_booking.services;

import com.gfreitash.flight_booking.exceptions.RoleCanNotBeOwnSubRoleException;
import com.gfreitash.flight_booking.services.dto.input.RoleInputDTO;
import com.gfreitash.flight_booking.exceptions.RoleDoesNotExistException;
import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolationException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
class RoleServiceTest {

    @Autowired
    private RoleService roleService;

    @Test
    @DisplayName("saveRole with valid input should return (new) saved role")
    void saveRoleTest1() {
        var roleName = "TestRole";
        var parentRoleName = "ROLE_ADMIN";


        var inputDTO = RoleInputDTO.builder()
                .name(roleName)
                .parentRole(parentRoleName)
                .build();
        var outputDTO = roleService.saveRole(inputDTO);

        Assertions.assertThat(outputDTO.name()).isEqualTo(roleName);
        Assertions.assertThat(outputDTO.parentRole()).isEqualTo(parentRoleName);
    }

    @Test
    @DisplayName("saveRole with non-null invalid parent role input should throw RoleDoesNotExistException")
    void saveRoleTest2() {
        var roleName = "TestRole";
        var parentRoleName = "ROLE_DOS_NOT_EXIST";

        var inputDTO = RoleInputDTO.builder()
                .name(roleName)
                .parentRole(parentRoleName)
                .build();

        Assertions.assertThatExceptionOfType(RoleDoesNotExistException.class)
                .isThrownBy(() -> roleService.saveRole(inputDTO));
    }

    @Test
    @DisplayName("saveRole with null parent role input should return (new) saved role")
    void saveRoleTest3() {
        var roleName = "TestRole";

        var inputDTO = RoleInputDTO.builder()
                .name(roleName)
                .parentRole(null)
                .build();
        var outputDTO = roleService.saveRole(inputDTO);

        Assertions.assertThat(outputDTO.name()).isEqualTo(roleName);
        Assertions.assertThat(outputDTO.parentRole()).isNull();
    }

    @Test
    @DisplayName("saveName with blank name input should throw ConstraintViolationException")
    void saveRoleTest4() {
        var parentRoleName = "ROLE_ADMIN";

        var nullNameInputDto = new RoleInputDTO(null, parentRoleName);

        Assertions.assertThatExceptionOfType(ConstraintViolationException.class)
                .isThrownBy(() -> roleService.saveRole(nullNameInputDto));

        var emptyInputNameDto = RoleInputDTO.builder()
                .name("")
                .parentRole(parentRoleName)
                .build();

        Assertions.assertThatExceptionOfType(ConstraintViolationException.class)
                .isThrownBy(() -> roleService.saveRole(emptyInputNameDto));
    }

    @Test
    @DisplayName("saveRole with parentRole same as name should throw IllegalArgumentException")
    void saveRoleTest5() {
        var roleName = "ROLE_ADMIN";
        var otherCaseRoleName = "role_admin";

        var inputDTO = RoleInputDTO.builder()
                .name(roleName)
                .parentRole(otherCaseRoleName)
                .build();

        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> roleService.saveRole(inputDTO));
    }

    @Test
    @DisplayName("updateRole with valid input should return updated role")
    void updateRoleTest1() {
        var roleId = "3";
        var roleName = "TestRole";
        var parentRoleName = "ROLE_ADMIN";

        var inputDTO = RoleInputDTO.builder()
                .name(roleName)
                .parentRole(parentRoleName)
                .build();

        var outputDTO = roleService.updateRole(roleId, inputDTO);

        Assertions.assertThat(outputDTO.name()).isEqualTo(roleName);
        Assertions.assertThat(outputDTO.parentRole()).isEqualTo(parentRoleName);
        Assertions.assertThat(outputDTO.id()).isEqualTo(Integer.parseInt(roleId));
    }

    @Test
    @DisplayName("updateRole with non-null invalid parent role input should throw RoleDoesNotExistException")
    void updateRoleTest2() {
        var roleId = "1";
        var roleName = "TestRole";
        var parentRoleName = "ROLE_DOS_NOT_EXIST";

        var inputDTO = RoleInputDTO.builder()
                .name(roleName)
                .parentRole(parentRoleName)
                .build();

        Assertions.assertThatExceptionOfType(RoleDoesNotExistException.class)
                .isThrownBy(() -> roleService.updateRole(roleId, inputDTO));
    }

    @Test
    @DisplayName("updateRole with null parent role input should return updated role")
    void updateRoleTest3() {
        var roleId = "1";
        var roleName = "TestRole";

        var inputDTO = RoleInputDTO.builder()
                .name(roleName)
                .parentRole(null)
                .build();

        var outputDTO = roleService.updateRole(roleId, inputDTO);

        Assertions.assertThat(outputDTO.name()).isEqualTo(roleName);
        Assertions.assertThat(outputDTO.parentRole()).isNull();
        Assertions.assertThat(outputDTO.id()).isEqualTo(Integer.parseInt(roleId));
    }

    @Test
    @DisplayName("updateRole with blank name input should throw ConstraintViolationException")
    void updateRoleTest4() {
        var roleId = "2";
        var parentRoleName = "ROLE_ADMIN";

        var nullNameInputDto = new RoleInputDTO(null, parentRoleName);

        Assertions.assertThatExceptionOfType(ConstraintViolationException.class)
                .isThrownBy(() -> roleService.updateRole(roleId, nullNameInputDto));

        var emptyInputNameDto = RoleInputDTO.builder()
                .name("")
                .parentRole(parentRoleName)
                .build();

        Assertions.assertThatExceptionOfType(ConstraintViolationException.class)
                .isThrownBy(() -> roleService.updateRole(roleId, emptyInputNameDto));
    }

    @Test
    @DisplayName("updateRole with non-existent role id should throw RoleDoesNotExistException")
    void updateRoleTest5() {
        var roleId = "-999999";
        var roleName = "TestRole";
        var parentRoleName = "ROLE_ADMIN";

        var inputDTO = RoleInputDTO.builder()
                .name(roleName)
                .parentRole(parentRoleName)
                .build();

        Assertions.assertThatExceptionOfType(RoleDoesNotExistException.class)
                .isThrownBy(() -> roleService.updateRole(roleId, inputDTO));
    }

    @Test
    @DisplayName("updateRole with parentRole same as name should throw IllegalArgumentException")
    void updateRoleTest6() {
        var roleId = "1";
        var roleName = "Role_Admin";
        var otherCaseRoleName = "ROLE_ADMIN";

        var inputDTO = RoleInputDTO.builder()
                .name(roleName)
                .parentRole(otherCaseRoleName)
                .build();

        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> roleService.updateRole(roleId, inputDTO));
    }

    @Test
    @DisplayName("updateRole with parentRole being own sub-role should throw RoleCanNotBeOwnSubRoleException")
    void updateRoleTest7() {
        var roleId = "1";
        var roleName = "ROLE_ADMIN";
        var parentRoleName = "ROLE_USER";

        var inputDTO = RoleInputDTO.builder()
                .name(roleName)
                .parentRole(parentRoleName)
                .build();

        Assertions.assertThatExceptionOfType(RoleCanNotBeOwnSubRoleException.class)
                .isThrownBy(() -> roleService.updateRole(roleId, inputDTO));
    }

    @Test
    @DisplayName("getRoleById with valid input should return role")
    void getRoleByIdTest1() {
        var roleName = "ROLE_TEST";
        var parentRoleName = "ROLE_ADMIN";

        var inputDTO = RoleInputDTO.builder()
                .name(roleName)
                .parentRole(parentRoleName)
                .build();
        var savedRole = roleService.saveRole(inputDTO);
        var outputDTO = roleService.getRoleById(String.valueOf(savedRole.id())).orElseThrow();

        Assertions.assertThat(outputDTO.name()).isEqualTo(roleName);
        Assertions.assertThat(outputDTO.parentRole()).isEqualTo(parentRoleName);
    }

    @Test
    @DisplayName("getRoleById with non-existent role id should return empty optional")
    void getRoleByIdTest2() {
        var roleId = "-999999";

        var outputDTO = roleService.getRoleById(roleId);

        Assertions.assertThat(outputDTO).isEmpty();
    }

    @Test
    @DisplayName("getRoleById with non numerical id should throw NumberFormatException")
    void getRoleByIdTest3() {
        var roleId = "not a number";

        Assertions.assertThatExceptionOfType(NumberFormatException.class)
                .isThrownBy(() -> roleService.getRoleById(roleId));
    }

    @Test
    @DisplayName("getRoleByName with valid input should return role")
    void getRoleByNameTest1() {
        var roleName = "ROLE_TEST";
        var parentRoleName = "ROLE_ADMIN";

        var inputDTO = RoleInputDTO.builder()
                .name(roleName)
                .parentRole(parentRoleName)
                .build();
        var savedRole = roleService.saveRole(inputDTO);
        var outputDTO = roleService.getRoleByName(roleName).orElseThrow();

        Assertions.assertThat(outputDTO.name()).isEqualTo(roleName);
        Assertions.assertThat(outputDTO.parentRole()).isEqualTo(parentRoleName);
        Assertions.assertThat(outputDTO.id()).isEqualTo(savedRole.id());
    }

    @Test
    @DisplayName("getRoleByName with non-existent role name should return empty optional")
    void getRoleByNameTest2() {
        var roleName = "ROLE_DOES_NOT_EXIST";

        var outputDTO = roleService.getRoleByName(roleName);

        Assertions.assertThat(outputDTO).isEmpty();
    }

    @Test
    @DisplayName("getAllRoles should return non-empty list")
    void getAllRolesTest1() {
        var outputDTOList = roleService.getAllRoles();

        Assertions.assertThat(outputDTOList).isNotEmpty();
    }

    @Test
    @DisplayName("getAllRole with valid input should return non-empty list")
    void getAllRolesTest2() {
        var pageable = PageRequest.of(0, 1);

        var outputDTOList = roleService.getAllRoles(pageable);
        Assertions.assertThat(outputDTOList).isNotEmpty();
    }

    @Test
    @DisplayName("deleteRole with valid input should then not be able to find deleted role")
    void deleteRoleTest1() {
        var roleId = "1";

        roleService.deleteRole(1);

        Assertions.assertThat(roleService.getRoleById(roleId)).isEmpty();
    }
}