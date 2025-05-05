package com.example.desafio2cs.factory;

import com.example.desafio2cs.strategy.*;

public class CommissionStrategyFactory {

    public static CommissionStrategy getCommissionStrategy(String type) {
        switch (type) {
            case "Junior":
                return new JuniorCommissionStrategy();
            case "Senior":
                return new SeniorCommissionStrategy();
            case "Freelance":
                return new FreelanceCommissionStrategy();
            default:
                throw new IllegalArgumentException("Tipo de vendedor inválido");
        }
    }
}