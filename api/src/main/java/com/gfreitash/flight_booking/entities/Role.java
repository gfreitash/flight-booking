package com.gfreitash.flight_booking.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.io.Serializable;


@Entity
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Role implements Serializable {
    @Id
    private Integer id;

    @NotNull
    private String name;

    @OneToOne
    @JoinColumn(name="parent_role_id")
    private Role parentRole;

    public Role(String name, Role parentRole) {
        this.name = name;
        this.parentRole = parentRole;
    }
}
