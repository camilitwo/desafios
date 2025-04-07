package com.desafio.solucion_desafio_1.model.impl;

import com.desafio.solucion_desafio_1.enums.RoleType;
import com.desafio.solucion_desafio_1.model.UserModel;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Entity
@DiscriminatorValue("GUEST")
@AllArgsConstructor
public class GuestUserModel extends UserModel {

    public GuestUserModel(String id, String username, String email) {
        super(id, username, email, RoleType.GUEST);
    }

    @Override
    public boolean hasPermission(String permission) {
        return true;
    }
}

