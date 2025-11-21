class VeiculoFactoryTest {

    @Test
    void deveCriarVeiculoComPlaca() {
        VeiculoFactory factory = new VeiculoFactory();
        Veiculo v = factory.criarVeiculo("ABC1234");

        assertEquals("ABC1234", v.getPlaca());
    }
}

