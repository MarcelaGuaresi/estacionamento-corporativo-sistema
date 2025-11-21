class CalculoAvulsoTest {

    @Test
    void deveCalcularValorAvulso() {
        ICalculoCobranca calc = new CalculoAvulso();
        double result = calc.calcular(10, 5);
        assertEquals(50.0, result);
    }
}

