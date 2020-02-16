package com.example.gerenciacastracoes.negocio.exceccoes.Cliente;

public class ClienteJaEstaNaListaNegraException extends Exception {

    public ClienteJaEstaNaListaNegraException(){
        super("Este Telefone já está cadastrado na LISTA NEGRA!");
    }
}
