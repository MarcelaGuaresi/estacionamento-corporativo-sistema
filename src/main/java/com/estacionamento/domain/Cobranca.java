package com.estacionamento.domain;

public class Cobranca {

    private String placa;
    private double valor;
    private SituacaoPagamento situacao;

    public Cobranca(String placa, double valor) {
        this.placa = placa;
        this.valor = valor;
        this.situacao = SituacaoPagamento.ENVIADO;
    }

    public String getPlaca() {
        return placa;
    }

    public double getValor() {
        return valor;
    }

    public SituacaoPagamento getSituacao() {
        return situacao;
    }

    public void setSituacao(SituacaoPagamento situacao) {
        this.situacao = situacao;
    }
}

