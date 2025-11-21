public class IntegracaoRH {

    private static IntegracaoRH instancia;

    private IntegracaoRH() { }

    public static IntegracaoRH getInstance() {
        if(instancia == null) instancia = new IntegracaoRH();
        return instancia;
    }

    public void enviarDados(Funcionario f, Date ent, Date sai) { }
}

