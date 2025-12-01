package com.estacionamento.service;

import com.estacionamento.domain.*;
import com.estacionamento.factory.VeiculoFactory;
import com.estacionamento.pattern.singleton.GestorVagas;
import com.estacionamento.pattern.singleton.IntegracaoRH;
import com.estacionamento.repository.*;


import java.util.Date;

public class RegistroEntradaService {

    private FuncionarioRepo funcionarioRepo;
    private VisitanteRepo visitanteRepo;
    private VeiculoRepo veiculoRepo;
    private MovimentacaoRepo movimentacaoRepo;
    private VeiculoFactory veiculoFactory;
    private IntegracaoRH integracaoRH;
    private GestorVagas gestorVagas;

    public RegistroEntradaService(FuncionarioRepo funcionarioRepo,
                                  VisitanteRepo visitanteRepo,
                                  VeiculoRepo veiculoRepo,
                                  MovimentacaoRepo movimentacaoRepo,
                                  VeiculoFactory veiculoFactory,         IntegracaoRH integracaoRH, GestorVagas gestorVagas
) {

        this.funcionarioRepo = funcionarioRepo;
        this.visitanteRepo = visitanteRepo;
        this.veiculoRepo = veiculoRepo;
        this.movimentacaoRepo = movimentacaoRepo;
        this.veiculoFactory = veiculoFactory;
        this.integracaoRH = integracaoRH;
        this.gestorVagas = gestorVagas;
    }


    public boolean validarAcesso(String placa) {

        Veiculo veiculo = veiculoRepo.buscar(placa);

       
        if (veiculo == null) {
            notificarRH("Acesso negado: placa não cadastrada: " + placa);
            return false;
        }

        // 1. Verifica se é funcionário
        Funcionario f = funcionarioRepo.buscarPorPlaca(placa);
        if (f != null) {
            return true;
        }

        // 2. Verifica se é visitante com credencial válida
        Visitante v = visitanteRepo.buscarPorPlaca(placa);
        if (v != null && visitanteValido(v)) {
            return true;
        }

   
        notificarRH("Acesso negado: placa cadastrada mas sem permissão: " + placa);
        return false;
    }

   
    private boolean visitanteValido(Visitante v) {
        // Aqui entraria:
        // - QR Code válido
        // - Dentro da data
        // - Não expirado
        return true;
    }

    public boolean registrarEntrada(String placa) {
        

        if (!validarAcesso(placa)) {
            return false;
        }

        Veiculo veiculo = veiculoRepo.buscar(placa);

       
       boolean ocupou = this.gestorVagas.ocuparVaga(veiculo);

        if (!ocupou) {
            notificarRH("Acesso autorizado, mas SEM vagas disponíveis: " + placa);
            return false;
        }

     
        Movimentacao mov = new Movimentacao();
        mov.setPlaca(placa);
        mov.setHoraEntrada(new Date());

        movimentacaoRepo.registrar(mov);

        return true;
    }

   
    private void notificarRH(String msg) {
    this.integracaoRH.enviarAlerta(msg);
}
}
