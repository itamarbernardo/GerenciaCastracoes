/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.example.gerenciacastracoes.negocio;

import com.example.gerenciacastracoes.negocio.dados.IRepositorioListaNegra;
import com.example.gerenciacastracoes.negocio.dados.RepositorioListaNegra;
import com.example.gerenciacastracoes.negocio.entidades.Cliente;
import com.example.gerenciacastracoes.negocio.exceccoes.Cliente.ClienteJaAdicionadoException;
import com.example.gerenciacastracoes.negocio.exceccoes.Cliente.ClienteNaoExisteException;

import java.util.List;


/**
 *
 * @author Itamar Jr
 */
public class NegocioListaNegra {
    
    private IRepositorioListaNegra repositorio;

    public NegocioListaNegra() {
        this.repositorio = new RepositorioListaNegra();
    }

    public void adicionarCliente(Cliente c) throws ClienteJaAdicionadoException {

        Cliente cliente = repositorio.procurarCliente(c.getCodigo());

            if (cliente == null) {

                repositorio.adicionarCliente(c);

            } else {
                throw new ClienteJaAdicionadoException();
            }
         
    }

    public Cliente buscarCliente(int codigo) {
        return repositorio.procurarCliente(codigo);

    }

    public Cliente buscarCliente(String telefone) {
        return repositorio.procurarCliente(telefone);

    }

    public void removerCliente(int codigo) throws ClienteNaoExisteException {
        Cliente cliente = repositorio.procurarCliente(codigo);
        if (cliente != null) {
            repositorio.removerCliente(cliente);
        } else {
            throw new ClienteNaoExisteException();
        }

    }

    public void removerCliente(String telefone) throws ClienteNaoExisteException {
        Cliente cliente = repositorio.procurarCliente(telefone);
        if (cliente != null) {
            repositorio.removerCliente(cliente);
        } else {
            throw new ClienteNaoExisteException();
        }

    }

    public void alterarCliente(Cliente c) throws ClienteNaoExisteException {

        int index = repositorio.listagemClientesListaNegra().indexOf(c);

        if (index == -1) {
            throw new ClienteNaoExisteException();
        } else {
            repositorio.alterarCliente(c);
        }

    }

    public List<Cliente> listagemClientesListaNegra() {
        return repositorio.listagemClientesListaNegra();

    }
    
    
    
}
