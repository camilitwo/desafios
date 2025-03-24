package com.desafiojg.desafiojg.factory;

import com.desafiojg.desafiojg.model.User;

public interface UserFactory {
    User createUser(String username, String password);
}