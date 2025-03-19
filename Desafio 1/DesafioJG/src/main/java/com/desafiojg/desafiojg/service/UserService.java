package com.desafiojg.desafiojg.service;

import com.desafiojg.desafiojg.factory.*;
import com.desafiojg.desafiojg.factory.UserFactory;
import com.desafiojg.desafiojg.model.User;
import com.desafiojg.desafiojg.model.UserRole;
import com.desafiojg.desafiojg.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User createUser(String username, String password, UserRole role) {
        UserFactory factory = getFactory(role); // Obtiene la fábrica correspondiente al rol
        User user = factory.createUser(username, password); // Crea el usuario usando la fábrica
        return userRepository.save(user); // Guarda el usuario en la base de datos
    }

    private UserFactory getFactory(UserRole role) {
        switch (role) {
            case ADMIN:
                return new AdminFactory();
            case USER:
                return new DefaultUserFactory();
            case GUEST:
                return new GuestFactory();
            default:
                throw new IllegalArgumentException("Rol no válido: " + role);
        }
    }
}