package com.desafiojg.desafiojg.factory;

import com.desafiojg.desafiojg.model.User;
import com.desafiojg.desafiojg.model.UserRole;

public class DefaultUserFactory implements UserFactory {
    @Override
    public User createUser(String username, String password) {
        return new User(username, password, UserRole.USER);
    }
}
