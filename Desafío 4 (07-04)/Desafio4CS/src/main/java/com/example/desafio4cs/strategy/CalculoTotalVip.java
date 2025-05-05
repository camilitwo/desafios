package com.example.desafio4cs.strategy;

public class CalculoTotalVip implements CalculoTotalStrategy {
    @Override
    public double calcular(double totalOriginal) {
        return totalOriginal * 0.90; // 10% de descuento
    }
}
