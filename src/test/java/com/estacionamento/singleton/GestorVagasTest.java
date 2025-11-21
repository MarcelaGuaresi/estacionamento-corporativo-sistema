package com.estacionamento.pattern.singleton;

import com.estacionamento.domain.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GestorVagasTest {

    @Test
    void deveOcuparPrimeiraVagaLivre() {

        GestorVagas gestor = GestorVagas.getInstance();

        Vaga v1 = new Vaga(1);
        Vaga v2 = new Vaga(2);

        gestor.adicionarVaga(v1);
        gestor.adicionarVaga(v2);

        Veiculo veiculo = new Veiculo("ABC1234");

        assertTrue(gestor.ocuparVaga(veiculo));
        assertEquals(StatusVaga.OCUPADA, v1.getStatus());
    }
}
