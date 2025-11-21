public class ReconhecimentoPlacas implements ICameraObserver {

    private static ReconhecimentoPlacas instancia;

    public static ReconhecimentoPlacas getInstance() {
        if(instancia == null) instancia = new ReconhecimentoPlacas();
        return instancia;
    }

    @Override
    public void notificar(String placa, Image img) { }
}

