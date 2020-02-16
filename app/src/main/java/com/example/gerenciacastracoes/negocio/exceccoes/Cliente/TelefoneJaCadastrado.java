package com.example.gerenciacastracoes.negocio.exceccoes.Cliente;

public class TelefoneJaCadastrado extends Exception {

    public TelefoneJaCadastrado(){
        super("Já existe um cliente cadastrado com esse Telefone neste mutirão!");
    }
}
