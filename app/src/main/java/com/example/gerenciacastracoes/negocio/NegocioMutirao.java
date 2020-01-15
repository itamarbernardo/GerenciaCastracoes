/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.gerenciacastracoes.negocio;

import com.example.gerenciacastracoes.negocio.dados.IRepositorioMutirao;
import com.example.gerenciacastracoes.negocio.dados.RepositorioMutirao;
import com.example.gerenciacastracoes.negocio.entidades.Mutirao;
import com.example.gerenciacastracoes.negocio.exceccoes.mutirao.MutiraoJaExisteException;
import com.example.gerenciacastracoes.negocio.exceccoes.mutirao.MutiraoNaoExisteException;

import java.time.LocalDate;
import java.util.List;

/**
 *
 * @author Itamar Jr
 */
public class NegocioMutirao {

    private IRepositorioMutirao repositorio;

    public NegocioMutirao() {
        this.repositorio = new RepositorioMutirao();
    }

    public void adicionarMutirao(Mutirao m) throws MutiraoJaExisteException {

        Mutirao mutirao = repositorio.procurarMutirao(m.getCodigo());

            if (mutirao == null) {

                repositorio.adicionarMutirao(m);

            } else {
                throw new MutiraoJaExisteException();
            }
         
    }

    public Mutirao buscarMutirao(LocalDate data) {
        return repositorio.procurarMutirao(data);

    }

    public Mutirao buscarMutirao(int codigo) {
        return repositorio.procurarMutirao(codigo);

    }

    public void removerMultirao(LocalDate data) throws MutiraoNaoExisteException {
        Mutirao mutirao = repositorio.procurarMutirao(data);
        if (mutirao != null) {
            repositorio.removerMutirao(mutirao);
        } else {
            throw new MutiraoNaoExisteException();
        }

    }

    public void removerMultirao(int codigo) throws MutiraoNaoExisteException {
        Mutirao mutirao = repositorio.procurarMutirao(codigo);
        if (mutirao != null) {
            repositorio.removerMutirao(mutirao);
        } else {
            throw new MutiraoNaoExisteException();
        }

    }

    public void alterarMutirao(Mutirao m) throws MutiraoNaoExisteException {

        int index = repositorio.listagemMutiroes().indexOf(m);

        if (index == -1) {
            throw new MutiraoNaoExisteException();
        } else {
            repositorio.alterarMutirao(m);
        }

    }

    public List<Mutirao> listagemMutiroes() {
        return repositorio.listagemMutiroes();

    }
    
    

}
