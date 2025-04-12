package com.desafios.sistordenes.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;

    private Integer cantidad;

    private Double precioUnitario;

    @ManyToOne
    @JoinColumn(name = "orden_id") // clave foránea
    private Orden orden;
}
