package com.desafio.desafio2.service;

public class FreelanceCommissionStrategy implements CommissionStrategy {
    @Override
    public double calculateCommission(double totalSales) {
        return totalSales > 1_000_000 ? totalSales* 0.15 : totalSales * 0.08;
    }
}
