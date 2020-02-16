package com.example.gerenciacastracoes.negocio.exceccoes.Cliente;

public class TelefoneJaCadastradoNaListaEspera extends Exception {

    public TelefoneJaCadastradoNaListaEspera(){
        super("Já existe um cliente cadastrado com esse Telefone na Lista de Espera deste mutirão!");
    }

}
