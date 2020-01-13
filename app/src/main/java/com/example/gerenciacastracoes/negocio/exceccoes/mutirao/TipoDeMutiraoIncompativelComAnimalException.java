package com.example.gerenciacastracoes.negocio.exceccoes.mutirao;

public class TipoDeMutiraoIncompativelComAnimalException extends Exception {

    public TipoDeMutiraoIncompativelComAnimalException(String animal) {
        super("Este mutirão é apenas para" + animal);
    }
}
