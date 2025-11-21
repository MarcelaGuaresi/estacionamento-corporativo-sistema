public class CalculoMensalista implements ICalculoCobranca {
    @Override
    public double calcular(double valorHora, double horas) {
        return valorHora * horas * 0.7; // exemplo
    }
}

