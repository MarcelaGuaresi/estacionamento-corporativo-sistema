package com.estacionamento.pattern.singleton;

import com.estacionamento.domain.Vaga;
import com.estacionamento.domain.StatusVaga;
import com.estacionamento.domain.Veiculo;
import java.util.ArrayList;
import java.util.List;

public class GestorVagas {

    private static GestorVagas instancia;

    private List<Vaga> vagas = new ArrayList<>();

    private GestorVagas() { }

    public static GestorVagas getInstance() {
        if (instancia == null) {
            instancia = new GestorVagas();
        }
        return instancia;
    }

    public Vaga buscarVagaLivre() {
        for (Vaga v : vagas) {
            if (v.getStatus() == StatusVaga.LIVRE) {
                return v;
            }
        }
        return null; 
    }


    public boolean ocuparVaga(Veiculo veiculo) {
        Vaga vagaLivre = buscarVagaLivre();
        if (vagaLivre == null) {
            return false; // não há vagas
        }
        vagaLivre.ocupar(veiculo);
        return true;
    }

    public void liberarVaga(String placa) {
        for (Vaga v : vagas) {
            if (v.getVeiculo() != null && v.getVeiculo().getPlaca().equals(placa)) {
                v.liberar();
            }
        }
    }

    public List<Vaga> consultarVagas() {
        return vagas;
    }


    public void adicionarVaga(Vaga v) {
        vagas.add(v);
    }
}
