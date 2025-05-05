package com.example.desafio4cs.strategy;

public class CalculoTotalNormal implements CalculoTotalStrategy {
    @Override
    public double calcular(double totalOriginal) {
        return totalOriginal;
    }
}
