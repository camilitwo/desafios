package com.example.desafio4cs.strategy;

import com.example.desafio4cs.model.TipoCliente;

public class CalculoTotalFactory {

    public static CalculoTotalStrategy obtenerEstrategia(TipoCliente tipoCliente) {
        return switch (tipoCliente) {
            case NORMAL -> new CalculoTotalNormal();
            case FRECUENTE -> new CalculoTotalFrecuente();
            case VIP -> new CalculoTotalVip();
        };
    }
}
