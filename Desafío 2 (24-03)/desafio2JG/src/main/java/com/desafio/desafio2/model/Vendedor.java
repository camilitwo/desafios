package com.desafio.desafio2.model;

import jakarta.persistence.*;

@Entity
@Table(name = "vendedores")
public class Vendedor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoVendedor tipo; // JUNIOR, SENIOR, FREELANCE

    // Getters y Setters
    public Long getId() { return id; }
    public String getNombre() { return nombre; }
    public TipoVendedor getTipo() { return tipo; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public void setTipo(TipoVendedor tipo) { this.tipo = tipo; }
}