package com.estacionamento.factory;
import com.estacionamento.domain.Veiculo;
public class VeiculoFactory {

    public Veiculo criarVeiculo(String placa) {
        return new Veiculo(placa);
    }
}

