/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.example.gerenciacastracoes.negocio.exceccoes.mutirao;

/**
 *
 * @author Itamar Jr
 */
public class MutiraoJaExisteException extends Exception {

    public MutiraoJaExisteException() {
        super("Já existe um Mutirão com essa data");
    }
    
}
