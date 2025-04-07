package com.desafio.solucion_desafio_1.model.impl;

import com.desafio.solucion_desafio_1.enums.RoleType;
import com.desafio.solucion_desafio_1.model.UserModel;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Entity
@DiscriminatorValue("USER")
@AllArgsConstructor
public class RegularUserModel extends UserModel {
    public RegularUserModel(String id, String username, String email) {
        super(id, username, email, RoleType.USER);
    }

    @Override
    public boolean hasPermission(String permission) {
        return true;
    }
}

