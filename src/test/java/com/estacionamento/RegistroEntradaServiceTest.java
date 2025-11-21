package com.estacionamento.service;

import com.estacionamento.domain.*;
import com.estacionamento.factory.VeiculoFactory;
import com.estacionamento.pattern.singleton.GestorVagas;
import com.estacionamento.pattern.singleton.IntegracaoRH;
import com.estacionamento.repository.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Date;

class RegistroEntradaServiceTest {

    private FuncionarioRepo funcionarioRepo;
    private VisitanteRepo visitanteRepo;
    private VeiculoRepo veiculoRepo;
    private MovimentacaoRepo movimentacaoRepo;
    private VeiculoFactory veiculoFactory;

    private RegistroEntradaService service;

    @BeforeEach
    void setup() {
        funcionarioRepo = mock(FuncionarioRepo.class);
        visitanteRepo = mock(VisitanteRepo.class);
        veiculoRepo = mock(VeiculoRepo.class);
        movimentacaoRepo = mock(MovimentacaoRepo.class);
        veiculoFactory = mock(VeiculoFactory.class);

        service = new RegistroEntradaService(
                funcionarioRepo, visitanteRepo, veiculoRepo, movimentacaoRepo, veiculoFactory
        );
    }


    // =================================================================
    // TESTE 1: ACESSO FUNCIONÁRIO VÁLIDO
    // =================================================================
    @Test
    void devePermitirAcessoParaFuncionarioValido() {

        String placa = "ABC1234";

        when(veiculoRepo.buscar(placa)).thenReturn(new Veiculo(placa));
        when(funcionarioRepo.buscarPorPlaca(placa)).thenReturn(new Funcionario());

        assertTrue(service.validarAcesso(placa));
    }


    // =================================================================
    // TESTE 2: VISITANTE COM QR CODE VÁLIDO
    // =================================================================
    @Test
    void devePermitirAcessoParaVisitanteValido() {

        String placa = "XYZ9999";

        when(veiculoRepo.buscar(placa)).thenReturn(new Veiculo(placa));
        when(visitanteRepo.buscarPorPlaca(placa)).thenReturn(new Visitante());

        assertTrue(service.validarAcesso(placa));
    }


    // =================================================================
    // TESTE 3: ACESSO NEGADO — PLACA NÃO CADASTRADA
    // =================================================================
    @Test
    void deveNegarAcessoSePlacaNaoCadastrada() {

        String placa = "AAA0000";

        when(veiculoRepo.buscar(placa)).thenReturn(null);

        assertFalse(service.validarAcesso(placa));
    }


    // =================================================================
    // TESTE 4: REGISTRAR ENTRADA — GERA MOVIMENTAÇÃO E OCUPA VAGA
    // =================================================================
    @Test
    void registrarEntradaDeveGerarMovimentacaoEOcuparVaga() {

        String placa = "ABC1234";
        Veiculo v = new Veiculo(placa);

        when(veiculoRepo.buscar(placa)).thenReturn(v);
        when(funcionarioRepo.buscarPorPlaca(placa)).thenReturn(new Funcionario());

        GestorVagas gestor = GestorVagas.getInstance();
        gestor.adicionarVaga(new Vaga(1));

        boolean result = service.registrarEntrada(placa);

        assertTrue(result);
        verify(movimentacaoRepo, times(1)).registrar(any());
    }
}

