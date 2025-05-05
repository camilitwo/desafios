package com.example.desafio2cs.strategy;

public class FreelanceCommissionStrategy implements CommissionStrategy {
    @Override
    public double calculateCommission(double totalSales) {
        if (totalSales > 1_000_000) {
            return totalSales * 0.15;
        } else {
            return totalSales * 0.08;
        }
    }
}
