package com.desafios.sistordenes.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class OrdenResponse {
    private Long id;
    private String cliente;
    private LocalDate fecha;
    private Double total;
}
