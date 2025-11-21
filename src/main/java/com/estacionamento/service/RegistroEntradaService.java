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


    // -------------------------------------------------------
    //  RF07 & RF08 — Verificação de acesso
    // -------------------------------------------------------
    public boolean validarAcesso(String placa) {

        // 1. Verifica se a placa está cadastrada no sistema
        Veiculo veiculo = veiculoRepo.buscar(placa);

        if (veiculo == null) {
            notificarRH("Tentativa de acesso com placa NÃO cadastrada: " + placa);
            return false;
        }

        // 2. Verifica se pertence a funcionário
        Funcionario funcionario = funcionarioRepo.buscarPorPlaca(placa);
        if (funcionario != null) {
            return true; // Funcionário autorizado
        }

        // 3. Verifica se é visitante e se o QR Code é válido
        Visitante visitante = visitanteRepo.buscarPorPlaca(placa);
        if (visitante != null) {

            if (!visitanteRepositorioValidacao(visitante)) {
                notificarRH("Visitante com credencial inválida: " + placa);
                return false;
            }
            return true;
        }

        // 4. Se não for funcionário nem visitante → acesso negado
        notificarRH("Placa registrada mas sem permissão ativa: " + placa);
        return false;
    }


    // -------------------------------------------------------
    //  RF14 — Registro da entrada no sistema
    // -------------------------------------------------------
    public boolean registrarEntrada(String placa) {

        if (!validarAcesso(placa)) {
            return false;
        }

        // Criar movimentação
        Movimentacao mov = new Movimentacao();
        mov.setPlaca(placa);
        mov.setHoraEntrada(new Date());

        // Registrar movimentação no repositório
        movimentacaoRepo.registrar(mov);

        // Vaga é alocada automaticamente
        GestorVagas gestor = GestorVagas.getInstance();
        Veiculo veiculo = veiculoRepo.buscar(placa);

        boolean ocupou = gestor.ocuparVaga(veiculo);

        if (!ocupou) {
            notificarRH("Funcionário/Visitante autorizado, mas SEM VAGAS: " + placa);
            return false;
        }

        return true;
    }


    // -------------------------------------------------------
    // Método auxiliar: valida regra do QR Code do visitante
    // -------------------------------------------------------
    private boolean visitanteRepositorioValidacao(Visitante v) {
        // Aqui você colocaria:
        // - checagem de expirado
        // - se já foi usado
        // - horário permitido
        // mas deixamos lógico simples para o skeleton:
        return true;
    }

    // -------------------------------------------------------
    // Notificação automática ao RH (RF19)
    // -------------------------------------------------------
    private void notificarRH(String mensagem) {
        IntegracaoRH.getInstance().enviarAlerta(mensagem);
    }

}
