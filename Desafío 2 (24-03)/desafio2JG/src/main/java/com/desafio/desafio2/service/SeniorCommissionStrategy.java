package com.desafio.desafio2.service;

public class SeniorCommissionStrategy implements CommissionStrategy {
    @Override
    public double calculateCommission(double totalSales) {
        return totalSales * 0.1;
    }
}
