package com.desafiojg.desafiojg.factory;

import com.desafiojg.desafiojg.model.User;
import com.desafiojg.desafiojg.model.UserRole;

public class AdminFactory implements UserFactory {
    @Override
    public User createUser(String username, String password) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setRole(UserRole.ADMIN);
        return user;
    }
}