package com.example.usuarios.factory;

import com.example.usuarios.model.Administrador;
import com.example.usuarios.model.Invitado;
import com.example.usuarios.model.Usuario;
import com.example.usuarios.model.UsuarioNormal;

public class UsuarioFactory {

    public static Usuario crearUsuario(String nombre, String rol) {
        return switch (rol.toUpperCase()) {
            case "ADMIN" -> new Administrador(nombre);
            case "USER" -> new UsuarioNormal(nombre);
            case "GUEST" -> new Invitado(nombre);
            default -> throw new IllegalArgumentException("Rol no válido: " + rol);
        };
    }
}
