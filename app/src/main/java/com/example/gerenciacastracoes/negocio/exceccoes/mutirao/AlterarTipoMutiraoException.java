package com.example.gerenciacastracoes.negocio.exceccoes.mutirao;

public class AlterarTipoMutiraoException extends Exception{

    public AlterarTipoMutiraoException(String tipo, String animal){
        super("Não é possível alterar o mutirão para o tipo: " + tipo +" pois já existe pelo menos um " + animal + " já cadastrado.");

    }

}
