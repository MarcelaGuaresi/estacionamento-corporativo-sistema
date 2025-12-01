package com.estacionamento.pattern.singleton;

import com.estacionamento.domain.Funcionario;
import java.util.Date;

// Singleton com métodos de alerta e envio de dados para RH (RF17, RF19)
public class IntegracaoRH {

    private static IntegracaoRH instancia;

    private IntegracaoRH() { }

    public static IntegracaoRH getInstance() {
        if(instancia == null) instancia = new IntegracaoRH();
        return instancia;
    }

    // Usado pelo RegistroEntradaService para notificar problemas (RF19)
    public void enviarAlerta(String msg) {
        // Lógica de envio de log/email de alerta
    }
    
    // Versão simplificada usada pelo RegistroSaidaService (Cobrança)
    public void enviarDados(Funcionario f, double valorCobrado) {
        // Lógica: Enviar dados de valor para desconto em folha (RF17)
    }

    // Versão completa (UML)
    public void enviarDados(Funcionario f, Date ent, Date sai) { }
}
