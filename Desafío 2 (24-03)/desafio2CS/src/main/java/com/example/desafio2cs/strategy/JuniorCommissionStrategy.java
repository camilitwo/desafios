package com.example.desafio2cs.strategy;

public class JuniorCommissionStrategy implements CommissionStrategy {
    @Override
    public double calculateCommission(double totalSales) {
        return totalSales * 0.05;
    }
}