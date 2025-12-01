package com.estacionamento.pattern.strategy;

import com.estacionamento.pattern.strategy.ICalculoCobranca;
import com.estacionamento.pattern.strategy.CalculoMensalista;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
class CalculoMensalistaTest {

    @Test
    void deveCalcularValorMensalista() {
        ICalculoCobranca calc = new CalculoMensalista();
        double result = calc.calcular(10, 5);
        assertEquals(35.0, result);
    }
}

