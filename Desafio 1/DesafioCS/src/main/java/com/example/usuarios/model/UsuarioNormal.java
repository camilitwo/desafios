package com.example.usuarios.model;

public class UsuarioNormal extends Usuario {
    public UsuarioNormal(String nombre) {
        super(nombre, "USER");
    }

    @Override
    public String getPermisos() {
        return "Acceso limitado a funciones estándar.";
    }
}
