package com.desafio.desafio2.service;

import com.desafio.desafio2.model.TipoVendedor;
import org.springframework.stereotype.Component;

@Component
public class ComisionFactory {
    public CommissionStrategy getCommissionStrategy(String tipo) {
        TipoVendedor tipoVendedor = TipoVendedor.valueOf(tipo);
        switch (tipoVendedor) {
            case JUNIOR:
                return new JuniorCommissionStrategy();
            case SENIOR:
                return new SeniorCommissionStrategy();
            case FREELANCE:
                return new FreelanceCommissionStrategy();
            default:
                throw new IllegalArgumentException("Tipo no soportado: " + tipo);
        }
    }
}