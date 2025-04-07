package com.desafio.solucion_desafio_1.service;

import com.desafio.solucion_desafio_1.enums.RoleType;
import com.desafio.solucion_desafio_1.model.UserModel;

public interface UserService {
    UserModel createUser(String user, String email, RoleType role);
}
