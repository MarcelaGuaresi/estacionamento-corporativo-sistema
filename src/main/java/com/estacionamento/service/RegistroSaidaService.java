package com.estacionamento.service;

import com.estacionamento.domain.*;
import com.estacionamento.repository.*;
import com.estacionamento.pattern.singleton.GestorVagas;
import com.estacionamento.pattern.singleton.IntegracaoRH;
import com.estacionamento.pattern.strategy.ICalculoCobranca;

import java.util.Date;
import java.util.concurrent.TimeUnit;

public class RegistroSaidaService {

    private MovimentacaoRepo movimentacaoRepo;
    private VeiculoRepo veiculoRepo;
    private FuncionarioRepo funcionarioRepo;
    private ICalculoCobranca estrategiaCobranca;

    public RegistroSaidaService(MovimentacaoRepo movimentacaoRepo,
                                VeiculoRepo veiculoRepo,
                                FuncionarioRepo funcionarioRepo,
                                ICalculoCobranca estrategia) {

        this.movimentacaoRepo = movimentacaoRepo;
        this.veiculoRepo = veiculoRepo;
        this.funcionarioRepo = funcionarioRepo;
        this.estrategiaCobranca = estrategia;
    }

    // =========================================================
    // RF10 — CALCULAR TEMPO
    // =========================================================
    public double calcularTempo(String placa) {

        Movimentacao mov = movimentacaoRepo.buscarMovimentacaoAberta(placa);
        if (mov == null) {
            return 0;
        }

        Date agora = new Date();
        mov.setHoraSaida(agora);

        long diffMs = agora.getTime() - mov.getHoraEntrada().getTime();
        double horas = TimeUnit.MILLISECONDS.toMinutes(diffMs) / 60.0;

        mov.setDuracao(horas);
        movimentacaoRepo.atualizar(mov);

        return horas;
    }

    // =========================================================
    // REGISTRAR SAÍDA — RF16
    // =========================================================
    public boolean registrarSaida(String placa) {

        double horas = calcularTempo(placa);

        // Libera vaga
        GestorVagas.getInstance().liberarVaga(placa);

        return horas > 0;
    }

    // =========================================================
    // GERAR COBRANÇA — RF17
    // =========================================================
    public Cobranca gerarCobranca(String placa) {

        double horas = calcularTempo(placa);

        double valor = estrategiaCobranca.calcular(10.0, horas); // valor/hora hipotético

        Cobranca c = new Cobranca(placa, valor);

        // Se for funcionário → enviar dados ao RH (RF17)
        Funcionario f = funcionarioRepo.buscarPorPlaca(placa);
        if (f != null) {
            IntegracaoRH.getInstance().enviarDados(f, valor);
        }

        return c;
    }
}
