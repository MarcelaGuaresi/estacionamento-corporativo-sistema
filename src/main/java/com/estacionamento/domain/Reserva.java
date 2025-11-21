package com.estacionamento.domain;

import java.util.Date;

public class Reserva {

    private Vaga vaga;
    private Funcionario funcionario;
    private Date dataInicio;
    private Date dataFim;

    public Reserva(Vaga vaga, Funcionario funcionario, Date dataInicio, Date dataFim) {
        this.vaga = vaga;
        this.funcionario = funcionario;
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
    }

    public Vaga getVaga() {
        return vaga;
    }

    public Funcionario getFuncionario() {
        return funcionario;
    }

    public Date getDataInicio() {
        return dataInicio;
    }

    public Date getDataFim() {
        return dataFim;
    }
}

