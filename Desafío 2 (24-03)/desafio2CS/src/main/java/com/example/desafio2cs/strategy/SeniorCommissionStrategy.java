package com.example.desafio2cs.strategy;

public class SeniorCommissionStrategy implements CommissionStrategy {
    @Override
    public double calculateCommission(double totalSales) {
        return totalSales * 0.10;
    }
}
