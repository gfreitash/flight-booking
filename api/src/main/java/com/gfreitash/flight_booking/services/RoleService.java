package com.gfreitash.flight_booking.services;

import com.gfreitash.flight_booking.dto.input.RoleInputDTO;
import com.gfreitash.flight_booking.dto.output.RoleOutputDTO;
import com.gfreitash.flight_booking.entities.Role;
import com.gfreitash.flight_booking.repositories.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RoleService {

    private final RoleRepository roleRepository;

    public Role saveRole(RoleInputDTO role) {
        var parentRole = role.parentRole() != null ? roleRepository.findByName(role.parentRole()).orElse(null) : null;
        return roleRepository.save(Role.builder()
                .name(role.name())
                .parentRole(parentRole)
                .build());
    }

    public Role updateRole(String id, RoleInputDTO role) {
        var parentRole = role.parentRole() != null ? roleRepository.findByName(role.parentRole()).orElse(null) : null;
        var roleToUpdate = roleRepository.findById(Integer.parseInt(id)).orElseThrow();
        roleToUpdate.setName(role.name());
        roleToUpdate.setParentRole(parentRole);
        return roleRepository.save(roleToUpdate);
    }

    public Optional<RoleOutputDTO> getRoleById(String id) {
        return roleRepository.findById(Integer.parseInt(id)).map(RoleOutputDTO::new);
    }

    public Optional<RoleOutputDTO> getRoleByName(String name) {
        return roleRepository.findByName(name).map(RoleOutputDTO::new);
    }

    public List<RoleOutputDTO> getAllRoles() {
        return roleRepository.findAll().stream().map(RoleOutputDTO::new).toList();
    }

    public Page<RoleOutputDTO> getAllRoles(Pageable pagination) {
        return roleRepository.findAll(pagination).map(RoleOutputDTO::new);
    }

    public void deleteRole(Integer id) {
        roleRepository.deleteById(id);
    }
}
