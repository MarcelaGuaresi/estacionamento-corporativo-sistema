package com.estacionamento.domain; 

public class Vaga {
    private int numero;
    private StatusVaga status;
    private Veiculo veiculoOcupante; 

    public Vaga(int numero) {
        this.numero = numero;
        this.status = StatusVaga.LIVRE;
        this.veiculoOcupante = null;
    }
    
    public StatusVaga getStatus() { return status; }
    public Veiculo getVeiculo() { return veiculoOcupante; }
    public int getNumero() { return numero; }

    public void ocupar(Veiculo v) { 
        this.veiculoOcupante = v; 
        this.status = StatusVaga.OCUPADA; 
    }
    
    public void liberar() { 
        this.veiculoOcupante = null; 
        this.status = StatusVaga.LIVRE;
    }
}
