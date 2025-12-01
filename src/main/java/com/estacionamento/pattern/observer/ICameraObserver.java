package com.estacionamento.pattern.observer; 

import java.awt.Image; 

public interface ICameraObserver {
    void notificar(String placa, Image imagem);
}
