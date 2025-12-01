package com.estacionamento.pattern.strategy;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
class CalculoAvulsoTest {

    @Test
    void deveCalcularValorAvulso() {
        ICalculoCobranca calc = new CalculoAvulso();
        double result = calc.calcular(10, 5);
        assertEquals(50.0, result);
    }
}
