package com.desafiojg.desafiojg.service;

import com.desafiojg.desafiojg.factory.*;
import com.desafiojg.desafiojg.factory.UserFactory;
import com.desafiojg.desafiojg.model.User;
import com.desafiojg.desafiojg.model.UserRole;
import com.desafiojg.desafiojg.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService {


    private final UserRepository userRepository;

    public User createUser(String username, String password, UserRole role) {
        UserFactory factory = getFactory(role); // Obtiene la fábrica correspondiente al rol
        User user = factory.createUser(username, password); // Crea el usuario usando la fábrica
        return userRepository.save(user); // Guarda el usuario en la base de datos
    }

    private UserFactory getFactory(UserRole role) {
        return switch (role) {
            case ADMIN -> new AdminFactory();
            case USER -> new DefaultUserFactory();
            case GUEST -> new GuestFactory();
        };
    }
}
