/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.example.gerenciacastracoes.negocio.exceccoes.Cliente;

/**
 *
 * @author Itamar Jr
 */
public class ClienteJaAdicionadoException extends Exception {

    public ClienteJaAdicionadoException() {
        super("O cliente já foi adicionado.");
    }
    
    
    
}
