package com.gfreitash.flight_booking.controllers;

import com.gfreitash.flight_booking.controllers.assemblers.EntityModelAssembler;
import com.gfreitash.flight_booking.dto.input.UserInputDTO;
import com.gfreitash.flight_booking.dto.output.UserOutputDTO;
import com.gfreitash.flight_booking.dto.update.UserUpdateDTO;
import com.gfreitash.flight_booking.services.UserService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;
import java.util.function.Function;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;
    private final EntityModelAssembler<UserOutputDTO, UserController> userAssembler;

    public UserController(UserService userService) {
        this.userService = userService;
        this.userAssembler = new EntityModelAssembler<>(UserController.class);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<UserOutputDTO>> getOneUser(@PathVariable String id) {
        var selfLink = linkTo(methodOn(UserController.class).getOneUser(id)).withSelfRel();
        var linkToRole = linkTo(methodOn(RoleController.class).getOneRole(id)).withRel("role");

        return userService.getUserById(id)
                .map(user -> userAssembler.toModel(user, selfLink, linkToRole))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<EntityModel<UserOutputDTO>> getOneUserByEmail(@PathVariable String email) {
        var user = userService.getUserByEmail(email);
        if (user.isPresent()) {
            return getOneUser(String.valueOf(user.get().id()));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    public ResponseEntity<PagedModel<EntityModel<UserOutputDTO>>> getAllUsers(Pageable pagination) {
        var users = userService.getAllUsers(pagination);

        Function<EntityModel<UserOutputDTO>, Void> itemLinks = userModel -> {
            var userId = String.valueOf(Objects.requireNonNull(userModel.getContent()).id());
            var roleId = String.valueOf(userModel.getContent().role().id());

            userModel.add(linkTo(methodOn(UserController.class).getOneUser(userId)).withSelfRel());
            userModel.add(linkTo(methodOn(RoleController.class).getOneRole(roleId)).withRel("role"));
            return null;
        };

        var userCollectionModel = userAssembler.toCollectionModel(users.getContent(), itemLinks);
        var pagedModel = userAssembler.toPagedModel(users, userCollectionModel);

        return ResponseEntity.ok().body(pagedModel);
    }

    @PostMapping
    @Transactional
    public ResponseEntity<EntityModel<UserOutputDTO>> createUser(@RequestBody UserInputDTO user) {
        var newUser = userService.saveUser(user);
        return getOneUser(String.valueOf(newUser.getId()));
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<EntityModel<UserOutputDTO>> updateUser(@PathVariable String id, @RequestBody @Valid UserUpdateDTO user) {
        var updatedUser = userService.updateUser(id, user);
        return getOneUser(String.valueOf(updatedUser.getId()));
    }

    @PutMapping("/{id}/role")
    @Transactional
    public ResponseEntity<EntityModel<UserOutputDTO>> updateUserRole(@PathVariable String id, @RequestBody String role) {
        var updatedUser = userService.updateUserRole(id, role);
        return getOneUser(String.valueOf(updatedUser.getId()));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Void> deleteUser(@PathVariable String id) {
        var user = userService.getUserById(id);
        if (user.isPresent()) {
            userService.deleteUser(user.get().id());
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.notFound().build();
    }
}
