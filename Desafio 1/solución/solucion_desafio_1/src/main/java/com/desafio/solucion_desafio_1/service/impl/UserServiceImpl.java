package com.desafio.solucion_desafio_1.service.impl;

import com.desafio.solucion_desafio_1.enums.RoleType;
import com.desafio.solucion_desafio_1.factory.UserFactory;
import com.desafio.solucion_desafio_1.model.UserModel;
import com.desafio.solucion_desafio_1.repository.UserRepository;
import com.desafio.solucion_desafio_1.service.UserService;
import com.desafio.solucion_desafio_1.util.validator.UserValidator;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class UserServiceImpl implements UserService {
    private final UserFactory userFactory;
    private final UserRepository userRepository;
    private final UserValidator userValidator;

    @Override
    @Transactional
    public UserModel createUser(String username, String email, RoleType roleType) {
        userValidator.validate(username, email);
        var newUser = userFactory.createUser(username, email, roleType);
        return userRepository.save(newUser);
    }
}
