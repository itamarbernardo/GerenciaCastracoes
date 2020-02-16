package com.example.gerenciacastracoes.negocio.exceccoes.Animal;

public class QuantidadeAnimaisInsuficienteParaExcluirException extends Exception {

    public QuantidadeAnimaisInsuficienteParaExcluirException(){
        //super("Não é possível excluir, pois o cliente deve possuir pelo menos um animal!");
        super("Operação não pode ser executada! Para excluir, cadastre um novo animal e tente novamente.");
    }

}
