package com.desafio.desafio2.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "ventas")
public class Venta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne  // Relación Muchos-a-Uno con Vendedor
    @JoinColumn(name = "vendedor_id", nullable = false)
    private Vendedor vendedor;

    @Column(nullable = false)
    private double monto;

    @Column(name = "fecha_venta", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime fechaVenta;

    public void setFechaVenta(LocalDateTime fechaVenta) {
        this.fechaVenta = fechaVenta;
    }

// Getters y Setters
public Long getId() {
    return id;
}

public void setId(Long id) {
    this.id = id;
}

public Vendedor getVendedor() {
    return vendedor;
}

public void setVendedor(Vendedor vendedor) {
    this.vendedor = vendedor;
}

public double getMonto() {
    return monto;
}

public void setMonto(double monto) {
    this.monto = monto;
}

public LocalDateTime getFechaVenta() {
    return fechaVenta;
}
    @Column(name = "comision", nullable = false)
    private double comision; // Nuevo campo para almacenar la comisión calculada

    // ... getters y setters existentes

    public double getComision() {
        return comision;
    }

    public void setComision(double comision) {
        this.comision = comision;
    }
}
