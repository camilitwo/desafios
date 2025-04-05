package com.example.usuarios.model;

public class Administrador extends Usuario {
    public Administrador(String nombre) {
        super(nombre, "ADMIN");
    }

    @Override
    public String getPermisos() {
        return "Acceso completo al sistema.";
    }
}
