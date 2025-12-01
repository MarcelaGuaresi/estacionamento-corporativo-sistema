package com.estacionamento.pattern.singleton;

import com.estacionamento.domain.Funcionario;
import java.util.Date;


public class IntegracaoRH {

    private static IntegracaoRH instancia;

    private IntegracaoRH() { }

    public static IntegracaoRH getInstance() {
        if(instancia == null) instancia = new IntegracaoRH();
        return instancia;
    }

    
    public void enviarAlerta(String msg) {
        
    }
    
   
    public void enviarDados(Funcionario f, double valorCobrado) {
        
    }

    public void enviarDados(Funcionario f, Date ent, Date sai) { }
}
