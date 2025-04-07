package com.desafio.solucion_desafio_1.controller;

import com.desafio.solucion_desafio_1.dto.request.CreateUserRequest;
import com.desafio.solucion_desafio_1.model.UserModel;
import com.desafio.solucion_desafio_1.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Tag(name = "User API", description = "Operaciones relacionadas con los usuarios")
public class UserController {
    private final UserService userService;

    @Operation(summary = "Crea un usuario", description = "Crea un nuevo usuario en el sistema")
    @PostMapping
    public ResponseEntity<UserModel> createUser(@Valid @RequestBody CreateUserRequest request) {
        UserModel newUser = userService.createUser(
                request.getUsername(),
                request.getEmail(),
                request.getRoleType()
        );
        return new ResponseEntity<>(newUser, HttpStatus.CREATED);
    }

}
