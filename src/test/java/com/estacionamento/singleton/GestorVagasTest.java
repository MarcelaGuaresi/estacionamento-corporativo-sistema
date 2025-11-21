class GestorVagasTest {

    @Test
    void deveRetornarAMesmaInstancia() {
        GestorVagas g1 = GestorVagas.getInstance();
        GestorVagas g2 = GestorVagas.getInstance();

        assertSame(g1, g2);
    }
}

