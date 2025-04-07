package com.desafio.solucion_desafio_1.model;

import com.desafio.solucion_desafio_1.enums.RoleType;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "USERS")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "dtype", discriminatorType = DiscriminatorType.STRING)
@Data
@NoArgsConstructor
public abstract class UserModel {
    @Id
    private String id;
    private String username;
    private String email;

    @Enumerated(EnumType.STRING)
    private RoleType role;

    protected UserModel(String id, String username, String email, RoleType role) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.role = role;
    }

    public abstract boolean hasPermission(String permission);
}

