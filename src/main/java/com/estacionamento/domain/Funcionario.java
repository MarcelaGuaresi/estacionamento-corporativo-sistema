package com.estacionamento.domain;
import java.util.Date;

public class Funcionario extends Usuario {
    private String telefone;
    private Date dataNascimento;
    private String funcao;

    public Funcionario() { super("", ""); } 

    public Funcionario(String nome, String cpf, String telefone, Date dataNascimento, String funcao) {
        super(nome, cpf); 
        this.telefone = telefone;
        this.dataNascimento = dataNascimento;
        this.funcao = funcao;
    }
    
}
