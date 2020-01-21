package com.example.gerenciacastracoes.negocio.exceccoes.mutirao;

public class ErroParaGerarCodigoMutiraoException extends Exception {

    public ErroParaGerarCodigoMutiraoException(){
        super("Erro ao gerar o código do mutirão!");
    }
}
