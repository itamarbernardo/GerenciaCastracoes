/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.example.gerenciacastracoes.negocio.exceccoes.Animal;

/**
 *
 * @author Itamar Jr
 */
public class AnimalNaoExisteException extends Exception {

    public AnimalNaoExisteException() {
        super("Animal não está cadastrado");
    }
    
}
