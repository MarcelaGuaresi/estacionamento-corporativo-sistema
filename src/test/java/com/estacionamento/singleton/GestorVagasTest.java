package com.estacionamento.pattern.singleton;

import com.estacionamento.domain.*;
import org.junit.jupiter.api.AfterEach; 
import org.junit.jupiter.api.BeforeEach; 
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GestorVagasTest {

    private GestorVagas gestor;
    private Vaga v1;
    private Vaga v2;
    private Veiculo veiculo;

    @BeforeEach
    void setup() {
     
        gestor = GestorVagas.getInstance();
        
       
        gestor.consultarVagas().clear(); 
        
        v1 = new Vaga(1);
        v2 = new Vaga(2);
        veiculo = new Veiculo("ABC1234");
        
      
        gestor.adicionarVaga(v1);
        gestor.adicionarVaga(v2);
    }
    
    @AfterEach
    void tearDown() {
       
        gestor.consultarVagas().clear();
    }

    @Test
    void deveOcuparPrimeiraVagaLivre() {
        
       
        assertTrue(gestor.ocuparVaga(veiculo), "Deve ser capaz de ocupar uma vaga livre.");
        
        
        assertEquals(StatusVaga.OCUPADA, v1.getStatus(), "A primeira vaga (v1) deve estar OCUPADA.");
        
       
        assertEquals(StatusVaga.LIVRE, v2.getStatus(), "A segunda vaga (v2) deve estar LIVRE.");
        
       
        gestor.liberarVaga("ABC1234");
        assertEquals(StatusVaga.LIVRE, v1.getStatus(), "A vaga deve estar LIVRE após a liberação.");
    }
    
    @Test
    void deveNegarOcupacaoSeNaoHouverVagaLivre() {
        
        Veiculo veiculo2 = new Veiculo("DEF5678");
        gestor.ocuparVaga(veiculo); 
        gestor.ocuparVaga(veiculo2); 

        Veiculo veiculoExtra = new Veiculo("GHI9012");
        
       
        assertFalse(gestor.ocuparVaga(veiculoExtra), "Deve negar ocupação se todas as vagas estiverem ocupadas.");
    }
}
