package com.example.gerenciacastracoes.negocio.exceccoes.mutirao;

public class TipoDeMutiraoIncompativelComAnimalException extends Exception {

    public TipoDeMutiraoIncompativelComAnimalException(String tipoAnimal) {
        super("Este mutirão é apenas para " + tipoAnimal + "s");
    }
}
