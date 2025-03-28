package com.desafio.desafio2.service;

public class JuniorCommissionStrategy implements CommissionStrategy {
    @Override
    public double calculateCommission(double totalSales) {
        return totalSales * 0.05;
    }
}

// TODO:: implementar SeniorCommissionStrategy y FreelanceCommissionStrategy