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
    private GestorVagas gestorVagas; 
    private IntegracaoRH integracaoRH; 

 
    public RegistroSaidaService(MovimentacaoRepo movimentacaoRepo,
                                VeiculoRepo veiculoRepo,
                                FuncionarioRepo funcionarioRepo,
                                ICalculoCobranca estrategia,
                                GestorVagas gestorVagas,
                                IntegracaoRH integracaoRH) {

        this.movimentacaoRepo = movimentacaoRepo;
        this.veiculoRepo = veiculoRepo;
        this.funcionarioRepo = funcionarioRepo;
        this.estrategiaCobranca = estrategia;
        this.gestorVagas = gestorVagas;
        this.integracaoRH = integracaoRH;
    }

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

    public boolean registrarSaida(String placa) {
        double horas = calcularTempo(placa);

     
        this.gestorVagas.liberarVaga(placa); 

        return horas > 0;
    }

    public Cobranca gerarCobranca(String placa) {
        double horas = calcularTempo(placa);
        double valor = estrategiaCobranca.calcular(10.0, horas); 

        Cobranca c = new Cobranca(placa, valor); 

        Funcionario f = funcionarioRepo.buscarPorPlaca(placa);
        if (f != null) {
      
            this.integracaoRH.enviarDados(f, valor); 
        }

        return c;
    }
}
