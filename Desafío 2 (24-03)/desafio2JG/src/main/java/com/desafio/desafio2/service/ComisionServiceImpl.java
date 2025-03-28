package com.desafio.desafio2.service;

import com.desafio.desafio2.dto.ComisionResponse;
import com.desafio.desafio2.dto.VentaRequest;
import com.desafio.desafio2.model.TipoVendedor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public abstract class ComisionServiceImpl implements CommissionStrategy {
    private final ComisionFactory factory;

    @Autowired
    public ComisionServiceImpl(ComisionFactory factory) {
        this.factory = factory;
    }

    @Override
    public ComisionResponse calcularComision(VentaRequest request) {
        // Validaciones iniciales
        if (request == null || request.getTipoVendedor() == null || request.getMontoVendido() <= 0) {
            throw new IllegalArgumentException("La solicitud no es válida");
        }

        TipoVendedor tipo;
        try {
            tipo = TipoVendedor.valueOf(request.getTipoVendedor().toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Tipo de vendedor no válido: " + request.getTipoVendedor());
        }

        CommissionStrategy strategy = factory.getCommissionStrategy(tipo.name());
        if (strategy == null) {
            throw new IllegalArgumentException("Estrategia no encontrada para el tipo: " + tipo);
        }

        double comision = strategy.calculateCommission(request.getMontoVendido());

        // Crear la respuesta
        ComisionResponse response = new ComisionResponse();
        response.setComision(comision);
        response.setMensaje("Comisión calculada para " + tipo);
        return response;
    }
}