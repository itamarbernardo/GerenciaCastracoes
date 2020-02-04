package com.example.gerenciacastracoes.negocio.exceccoes.Animal;

public class QuantidadeAnimaisInsuficienteParaExcluirException extends Exception {

    public QuantidadeAnimaisInsuficienteParaExcluirException(){
        super("Não é possível excluir, pois o cliente possui apenas um animal!");
    }

}
