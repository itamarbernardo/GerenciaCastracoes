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
public class JaExisteMutiraoComEssaDataException extends Exception{

    public JaExisteMutiraoComEssaDataException() {
        super("Já existe um mutirão cadastrado com essa data.");
    }
    
    
}
