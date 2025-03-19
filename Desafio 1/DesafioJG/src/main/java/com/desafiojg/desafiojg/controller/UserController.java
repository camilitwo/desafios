package com.desafiojg.desafiojg.controller;

import com.desafiojg.desafiojg.model.User;
import com.desafiojg.desafiojg.model.UserRole;
import com.desafiojg.desafiojg.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users") // Ruta base para todos los endpoints de este controlador
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping // Endpoint para crear un usuario
    public User createUser(
            @RequestParam String username,
            @RequestParam String password,
            @RequestParam UserRole role) {
        return userService.createUser(username, password, role);
    }
}