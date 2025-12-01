package com.estacionamento.service;

import com.estacionamento.domain.*;
import com.estacionamento.pattern.singleton.GestorVagas;
import com.estacionamento.pattern.singleton.IntegracaoRH;
import com.estacionamento.pattern.strategy.ICalculoCobranca;
import com.estacionamento.repository.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*; 

class RegistroSaidaServiceTest {

    private MovimentacaoRepo movimentacaoRepo;
    private VeiculoRepo veiculoRepo;
    private FuncionarioRepo funcionarioRepo;
    private ICalculoCobranca estrategia;
    private GestorVagas gestorVagas;
    private IntegracaoRH integracaoRH;

    private RegistroSaidaService service;

    @BeforeEach
    void setup() {
        movimentacaoRepo = mock(MovimentacaoRepo.class);
        veiculoRepo = mock(VeiculoRepo.class);
        funcionarioRepo = mock(FuncionarioRepo.class);
        estrategia = mock(ICalculoCobranca.class);
        gestorVagas = mock(GestorVagas.class);
        integracaoRH = mock(IntegracaoRH.class);

        service = new RegistroSaidaService(
            movimentacaoRepo, veiculoRepo, funcionarioRepo, estrategia,
            gestorVagas, integracaoRH
        );

       
        doNothing().when(movimentacaoRepo).atualizar(any(Movimentacao.class));
    }

 
    @Test
    void calcularTempoDeveRetornarHorasCorretas() {

        String placa = "ABC1234";

        Movimentacao mov = new Movimentacao();
        mov.setPlaca(placa);
        mov.setHoraEntrada(new Date(System.currentTimeMillis() - (60 * 60 * 1000))); 

        when(movimentacaoRepo.buscarMovimentacaoAberta(placa)).thenReturn(mov);

        double horas = service.calcularTempo(placa);

        assertTrue(horas >= 1.0 && horas < 1.1);
        verify(movimentacaoRepo, times(1)).atualizar(any(Movimentacao.class));
    }

   
    @Test
    void gerarCobrancaDeveEnviarDadosAoRHSeFuncionario() {

        String placa = "FUN1234";
        double valorEsperado = 10.0;
        
        Movimentacao mov = new Movimentacao();
        mov.setHoraEntrada(new Date(System.currentTimeMillis() - (30 * 60 * 1000)));

        when(movimentacaoRepo.buscarMovimentacaoAberta(placa)).thenReturn(mov);
        when(funcionarioRepo.buscarPorPlaca(placa)).thenReturn(new Funcionario());
        when(estrategia.calcular(anyDouble(), anyDouble())).thenReturn(valorEsperado);
        
       
        
        Cobranca c = service.gerarCobranca(placa);

        assertEquals(valorEsperado, c.getValor());
        verify(integracaoRH, times(1)).enviarDados(any(Funcionario.class), eq(valorEsperado));
    }
    
  
    @Test
    void registrarSaidaDeveLiberarVaga() {
        String placa = "ABC1234";
    
    
    Movimentacao mov = new Movimentacao();
    
    mov.setHoraEntrada(new Date(System.currentTimeMillis() - 3600000)); 
    
    
    when(movimentacaoRepo.buscarMovimentacaoAberta(placa)).thenReturn(mov);
    
    
    doNothing().when(movimentacaoRepo).atualizar(any(Movimentacao.class)); 

 
    
    service.registrarSaida(placa);

   
    verify(gestorVagas, times(1)).liberarVaga(placa);
    }
}
