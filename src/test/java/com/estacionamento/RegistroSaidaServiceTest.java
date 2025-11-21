package com.estacionamento.service;

import com.estacionamento.domain.*;
import com.estacionamento.pattern.strategy.ICalculoCobranca;
import com.estacionamento.repository.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class RegistroSaidaServiceTest {

    private MovimentacaoRepo movimentacaoRepo;
    private VeiculoRepo veiculoRepo;
    private FuncionarioRepo funcionarioRepo;
    private ICalculoCobranca estrategia;

    private RegistroSaidaService service;

    @BeforeEach
    void setup() {
        movimentacaoRepo = mock(MovimentacaoRepo.class);
        veiculoRepo = mock(VeiculoRepo.class);
        funcionarioRepo = mock(FuncionarioRepo.class);
        estrategia = mock(ICalculoCobranca.class);

        service = new RegistroSaidaService(
                movimentacaoRepo, veiculoRepo, funcionarioRepo, estrategia
        );
    }

    // =======================================================
    // RF10 — CÁLCULO DO TEMPO DE PERMANÊNCIA
    // =======================================================
    @Test
    void calcularTempoDeveRetornarHorasCorretas() {

        String placa = "ABC1234";

        Movimentacao mov = new Movimentacao();
        mov.setPlaca(placa);
        mov.setHoraEntrada(new Date(System.currentTimeMillis() - (60 * 60 * 1000))); // 1 hora atrás

        when(movimentacaoRepo.buscarMovimentacaoAberta(placa)).thenReturn(mov);

        double horas = service.calcularTempo(placa);

        assertTrue(horas >= 1.0 && horas < 1.1);
        verify(movimentacaoRepo).atualizar(any());
    }

    // =======================================================
    // RF17 — ENVIO DA COBRANÇA AO RH
    // =======================================================
    @Test
    void gerarCobrancaDeveEnviarDadosAoRHSeFuncionario() {

        String placa = "FUN1234";

        Movimentacao mov = new Movimentacao();
        mov.setHoraEntrada(new Date(System.currentTimeMillis() - (30 * 60 * 1000))); // 30 min

        when(movimentacaoRepo.buscarMovimentacaoAberta(placa)).thenReturn(mov);
        when(funcionarioRepo.buscarPorPlaca(placa)).thenReturn(new Funcionario());
        when(estrategia.calcular(anyDouble(), anyDouble())).thenReturn(10.0);

        Cobranca c = service.gerarCobranca(placa);

        assertEquals(10.0, c.getValor());
    }
}
