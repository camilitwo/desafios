package com.desafio.desafio2.service;

import com.desafio.desafio2.dto.ComisionResponse;
import com.desafio.desafio2.dto.VentaRequest;

public class JuniorCommissionStrategy implements CommissionStrategy {
    @Override
    public double calculateCommission(double totalSales) {
        return totalSales * 0.05;
    }
}

// TODO:: implementar SeniorCommissionStrategy y FreelanceCommissionStrategy