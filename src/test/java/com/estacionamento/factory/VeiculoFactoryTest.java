package com.estacionamento.factory; 

import com.estacionamento.factory.VeiculoFactory; 
import com.estacionamento.domain.Veiculo;        
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class VeiculoFactoryTest {

    @Test
    void deveCriarVeiculoComPlaca() {
       
        VeiculoFactory factory = new VeiculoFactory();
        Veiculo v = factory.criarVeiculo("ABC1234");

      
        assertEquals("ABC1234", v.getPlaca());
    }
}
