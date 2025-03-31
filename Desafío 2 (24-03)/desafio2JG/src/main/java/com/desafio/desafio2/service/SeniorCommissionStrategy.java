package com.desafio.desafio2.service;

public abstract class SeniorCommissionStrategy implements CommissionStrategy {
    @Override
    public double calculateCommission(double totalSales) {
        return totalSales * 0.1;
    }
}
