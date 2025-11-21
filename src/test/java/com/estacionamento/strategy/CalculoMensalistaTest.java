class CalculoMensalistaTest {

    @Test
    void deveCalcularValorMensalista() {
        ICalculoCobranca calc = new CalculoMensalista();
        double result = calc.calcular(10, 5);
        assertEquals(35.0, result);
    }
}

