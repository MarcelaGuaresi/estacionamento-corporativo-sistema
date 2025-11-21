public class GestorVagas {

    private static GestorVagas instancia;
    private List<Vaga> vagas = new ArrayList<>();

    private GestorVagas() { }

    public static GestorVagas getInstance() {
        if(instancia == null) instancia = new GestorVagas();
        return instancia;
    }

    public List<Vaga> consultarVagas() { return vagas; }

    public void ocuparVaga(String placa) { }

    public void liberarVaga(String placa) { }
}

