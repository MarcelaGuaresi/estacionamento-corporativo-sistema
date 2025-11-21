package com.estacionamento.domain;

import java.util.Date;
import java.util.concurrent.TimeUnit;

public class Movimentacao {

    private String placa;
    private Date horaEntrada;
    private Date horaSaida;
    private double duracao; // em horas

    // GETTERS/SETTERS
    public String getPlaca() { return placa; }
    public void setPlaca(String placa) { this.placa = placa; }

    public Date getHoraEntrada() { return horaEntrada; }
    public void setHoraEntrada(Date horaEntrada) { this.horaEntrada = horaEntrada; }

    public Date getHoraSaida() { return horaSaida; }
    public void setHoraSaida(Date horaSaida) { this.horaSaida = horaSaida; }

    public double getDuracao() { return duracao; }

    /**
     * Calcula a duração real entre a horaEntrada e a horaSaida.
     * A duração é retornada em horas (com casas decimais).
     */
    public double calcularDuracao() {

        if (horaEntrada == null || horaSaida == null) {
            return 0;
        }

        long diffMillis = horaSaida.getTime() - horaEntrada.getTime();

        double horas = TimeUnit.MILLISECONDS.toMinutes(diffMillis) / 60.0;

        this.duracao = horas;
        return horas;
    }
}
