/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.example.gerenciacastracoes.negocio.dados;

import com.example.gerenciacastracoes.negocio.entidades.Mutirao;

import java.time.LocalDate;
import java.util.List;


/**
 *
 * @author Itamar Jr
 */
public interface IRepositorioMutirao {
    
    void adicionarMutirao(Mutirao m);
    void removerMutirao(Mutirao m);
    void alterarMutirao(Mutirao m);
    Mutirao procurarMutirao(int codigo);
    Mutirao procurarMutirao(LocalDate data);    
    List<Mutirao> listagemMutiroes();
    
    
}
