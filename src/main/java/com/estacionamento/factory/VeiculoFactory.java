public class VeiculoFactory {

    public Veiculo criarVeiculo(String placa) {
        return new Veiculo(placa);
    }
}

