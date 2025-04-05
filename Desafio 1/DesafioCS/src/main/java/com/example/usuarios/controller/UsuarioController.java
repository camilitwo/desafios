package com.example.usuarios.controller;

import com.example.usuarios.model.UsuarioEntity;
import com.example.usuarios.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping
    public UsuarioEntity crearUsuario(@RequestParam String nombre, @RequestParam String rol) {
        return usuarioService.crearUsuario(nombre, rol);
    }
}