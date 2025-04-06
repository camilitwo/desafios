package com.example.usuarios.model;

import jakarta.persistence.*;

@Entity
@Table(name = "usuarios")
public class UsuarioEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private String rol;
    private String permisos;

    public UsuarioEntity() {}

    public UsuarioEntity(String nombre, String rol, String permisos) {
        this.nombre = nombre;
        this.rol = rol;
        this.permisos = permisos;
    }

    // Getters y setters

    public Long getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getRol() {
        return rol;
    }

    public String getPermisos() {
        return permisos;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public void setPermisos(String permisos) {
        this.permisos = permisos;
    }
}
