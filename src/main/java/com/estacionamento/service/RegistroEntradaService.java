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

    public RegistroEntradaService(FuncionarioRepo funcionarioRepo,
                                  VisitanteRepo visitanteRepo,
                                  VeiculoRepo veiculoRepo,
                                  MovimentacaoRepo movimentacaoRepo,
                                  VeiculoFactory veiculoFactory) {

        this.funcionarioRepo = funcionarioRepo;
        this.visitanteRepo = visitanteRepo;
        this.veiculoRepo = veiculoRepo;
        this.movimentacaoRepo = movimentacaoRepo;
        this.veiculoFactory = veiculoFactory;
    }

    // =========================================================
    // VALIDAR ACESSO — RF07, RF08, RF19
    // =========================================================
    public boolean validarAcesso(String placa) {

        Veiculo veiculo = veiculoRepo.buscar(placa);

        // Se a placa não existe → acesso negado
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

        // 3. Não autorizado
        notificarRH("Acesso negado: placa cadastrada mas sem permissão: " + placa);
        return false;
    }

    // Exemplo simples de regra de credencial
    private boolean visitanteValido(Visitante v) {
        // Aqui entraria:
        // - QR Code válido
        // - Dentro da data
        // - Não expirado
        return true;
    }

    // =========================================================
    // REGISTRAR ENTRADA — RF14, RF15
    // =========================================================
    public boolean registrarEntrada(String placa) {

        if (!validarAcesso(placa)) {
            return false;
        }

        Veiculo veiculo = veiculoRepo.buscar(placa);

        GestorVagas gestor = GestorVagas.getInstance();
        boolean ocupou = gestor.ocuparVaga(veiculo);

        if (!ocupou) {
            notificarRH("Acesso autorizado, mas SEM vagas disponíveis: " + placa);
            return false;
        }

        // Registrar a movimentação (RF14)
        Movimentacao mov = new Movimentacao();
        mov.setPlaca(placa);
        mov.setHoraEntrada(new Date());

        movimentacaoRepo.registrar(mov);

        return true;
    }

    // =========================================================
    // RF19 — NOTIFICAÇÃO AO RH
    // =========================================================
    private void notificarRH(String msg) {
        IntegracaoRH.getInstance().enviarAlerta(msg);
    }
}
