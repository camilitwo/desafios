package com.desafios.sistordenes.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;
import lombok.Data;

import java.time.LocalDate;

@Data
public class OrdenRequest {

    @NotBlank(message = "El nombre del cliente no puede estar en blanco")
    private String cliente;

    @PastOrPresent(message = "La fecha debe ser pasada o de hoy")
    private LocalDate fecha;

    @Min(value = 1, message = "El total debe ser mayor o igual a 1")
    private Double total;
}
