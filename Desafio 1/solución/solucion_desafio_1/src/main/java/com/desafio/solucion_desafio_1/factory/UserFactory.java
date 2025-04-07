package com.desafio.solucion_desafio_1.factory;

import com.desafio.solucion_desafio_1.enums.RoleType;
import com.desafio.solucion_desafio_1.model.UserModel;
import com.desafio.solucion_desafio_1.model.impl.AdminUserModel;
import com.desafio.solucion_desafio_1.model.impl.GuestUserModel;
import com.desafio.solucion_desafio_1.model.impl.RegularUserModel;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.function.Function;

@Component
public class UserFactory {
    private final Map<RoleType, Function<UserParams, ? extends UserModel>> creators = new HashMap<>();

    public UserFactory() {
        creators.put(RoleType.ADMIN, params -> new AdminUserModel(params.id(), params.username(), params.email()));
        creators.put(RoleType.USER, params -> new RegularUserModel(params.id(), params.username(), params.email()));
        creators.put(RoleType.GUEST, params -> new GuestUserModel(params.id(), params.username(), params.email()));
    }

    public UserModel createUser(String username, String email, RoleType roleType) {
        String userId = UUID.randomUUID().toString();
        var params = new UserParams(userId, username, email);

        return Optional.ofNullable(creators.get(roleType))
                .orElseThrow(() -> new IllegalArgumentException("Tipo de rol inválido: " + roleType))
                .apply(params);
    }

    private record UserParams(String id, String username, String email) {}
}
