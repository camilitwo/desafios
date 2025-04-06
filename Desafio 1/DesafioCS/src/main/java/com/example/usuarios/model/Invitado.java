package com.example.usuarios.model;

public class Invitado extends Usuario {
    public Invitado(String nombre) {
        super(nombre, "GUEST");
    }

    @Override
    public String getPermisos() {
        return "Solo acceso de lectura.";
    }
}
