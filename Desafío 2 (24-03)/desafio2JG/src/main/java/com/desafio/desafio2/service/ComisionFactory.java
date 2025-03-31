package com.desafio.desafio2.service;

import com.desafio.desafio2.dto.ComisionResponse;
import com.desafio.desafio2.dto.VentaRequest;
import com.desafio.desafio2.model.TipoVendedor;
import org.springframework.stereotype.Component;

@Component
public class ComisionFactory {
    public CommissionStrategy getCommissionStrategy(String tipo) {
        TipoVendedor tipoVendedor = TipoVendedor.valueOf(tipo);
        switch (tipoVendedor) {
            case JUNIOR:
                return new JuniorCommissionStrategy() {
                    @Override
                    public ComisionResponse calcularComision(VentaRequest request) {
                        return null;
                    }
                };
            case SENIOR:
                return new SeniorCommissionStrategy() {
                        @Override
                        public ComisionResponse calcularComision(VentaRequest request) {
                            return null; // Implement custom behavior if necessary
                        }
                    };
            case FREELANCE:
                return new FreelanceCommissionStrategy() {
                        @Override
                        public ComisionResponse calcularComision(VentaRequest request) {
                            return null; // Implement custom behavior if necessary
                        }
                    };
            default:
                throw new IllegalArgumentException("Tipo no soportado: " + tipo);
        }
    }
}