
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
    

    private IntegracaoRH integracaoRH;
    private GestorVagas gestorVagas;
    
    private RegistroEntradaService service;

    @BeforeEach
    void setup() {

        funcionarioRepo = mock(FuncionarioRepo.class);
        visitanteRepo = mock(VisitanteRepo.class);
        veiculoRepo = mock(VeiculoRepo.class);
        movimentacaoRepo = mock(MovimentacaoRepo.class);
        veiculoFactory = mock(VeiculoFactory.class);
        
      
        integracaoRH = mock(IntegracaoRH.class);
        gestorVagas = mock(GestorVagas.class);

        service = new RegistroEntradaService(
            funcionarioRepo, visitanteRepo, veiculoRepo, movimentacaoRepo, veiculoFactory,
            integracaoRH, gestorVagas
        );
    }


  
    @Test
    void devePermitirAcessoParaFuncionarioValido() {

        String placa = "ABC1234";

        when(veiculoRepo.buscar(placa)).thenReturn(new Veiculo(placa));
        when(funcionarioRepo.buscarPorPlaca(placa)).thenReturn(new Funcionario());

        assertTrue(service.validarAcesso(placa));
    }


  
    @Test
    void devePermitirAcessoParaVisitanteValido() {

        String placa = "XYZ9999";

        when(veiculoRepo.buscar(placa)).thenReturn(new Veiculo(placa));
        when(visitanteRepo.buscarPorPlaca(placa)).thenReturn(new Visitante("Mock Visitante", "123456"));

        assertTrue(service.validarAcesso(placa));
    }


    @Test
    void deveNegarAcessoSePlacaNaoCadastrada() {

        String placa = "AAA0000";

        when(veiculoRepo.buscar(placa)).thenReturn(null);

        assertFalse(service.validarAcesso(placa));
        
  
        verify(integracaoRH, times(1)).enviarAlerta(anyString()); 
    }


   
    @Test
    void registrarEntradaDeveGerarMovimentacaoEOcuparVaga() {

        String placa = "ABC1234";
        Veiculo v = new Veiculo(placa);

       
        when(veiculoRepo.buscar(placa)).thenReturn(v);
        when(funcionarioRepo.buscarPorPlaca(placa)).thenReturn(new Funcionario());

      
        when(gestorVagas.ocuparVaga(v)).thenReturn(true);

        boolean result = service.registrarEntrada(placa);

   
        assertTrue(result);

        verify(gestorVagas, times(1)).ocuparVaga(v);
        
        verify(movimentacaoRepo, times(1)).registrar(any());
     
        verify(integracaoRH, never()).enviarAlerta(anyString());
    }
}
