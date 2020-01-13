/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.example.gerenciacastracoes.negocio.dados;

import com.example.gerenciacastracoes.negocio.entidades.Cliente;

import java.util.List;

/**
 *
 * @author Itamar Jr
 */
public interface IRepositorioListaNegra {
    
    void adicionarCliente(Cliente c);
    void removerCliente(Cliente c);
    void alterarCliente(Cliente c);
    Cliente procurarCliente(int codigo);
    Cliente procurarCliente(String telefone);    
    List<Cliente> listagemClientesListaNegra();
    
    
}
