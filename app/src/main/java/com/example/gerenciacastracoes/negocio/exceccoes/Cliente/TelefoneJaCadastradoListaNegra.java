package com.example.gerenciacastracoes.negocio.exceccoes.Cliente;

public class TelefoneJaCadastradoListaNegra extends Exception {

    public TelefoneJaCadastradoListaNegra(){
        super("Já existe um cliente cadastrado com esse Telefone!");
    }
}
