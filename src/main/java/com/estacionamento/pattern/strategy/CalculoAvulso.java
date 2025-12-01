package com.estacionamento.pattern.strategy;

public class CalculoAvulso implements ICalculoCobranca {
    @Override
    public double calcular(double valorHora, double horas) {
        return valorHora * horas;
    }
}
