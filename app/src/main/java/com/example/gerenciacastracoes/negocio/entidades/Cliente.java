/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.gerenciacastracoes.negocio.entidades;

import com.example.gerenciacastracoes.negocio.exceccoes.Animal.AnimalJaAdicionadoException;
import com.example.gerenciacastracoes.negocio.exceccoes.Animal.AnimalNaoExisteException;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


/**
 *
 * @author Itamar Jr
 */
public class Cliente implements Serializable{

    private int codigo;
    private String nome;
    private String telefone;
    private List<Animal> animais;
    private String tipoDePagamento;
    private boolean pagou;

    public Cliente(int codigo, String nome, String telefone, String tipoDePagamento, boolean pagou) {
        this.nome = nome;
        this.telefone = telefone;
        this.pagou = pagou;
        this.tipoDePagamento = tipoDePagamento;
        this.animais = new ArrayList<>();
        this.codigo = codigo;

    }

    public Cliente(int codigo, String nome, String telefone, String tipoDePagamento) {
        this.nome = nome;
        this.telefone = telefone;
        this.codigo = codigo;
        this.pagou = false;
        this.tipoDePagamento = tipoDePagamento;
        this.animais = new ArrayList<>();

    }

    public void adicionarAnimal(Animal a) throws AnimalJaAdicionadoException {
        if (animais.indexOf(a) == -1) {
            this.animais.add(a);
        } else{
            throw new AnimalJaAdicionadoException();
        }
    }
    
    public Animal procurarAnimal(int codigo){
        for(Animal animal : animais){
            if(animal.getCodigo() == codigo){
                return animal;
            }
        }
        return null;
    }
    
    public void alterarAnimal(Animal a) throws AnimalNaoExisteException {
        int index = animais.indexOf(a);
        
        if (index != -1) {
            this.animais.set(index, a);
        } else{
            throw new AnimalNaoExisteException();
        }
    }

    public void removerAnimal(int codigo) throws AnimalNaoExisteException {
        Animal animal = procurarAnimal(codigo);

        if (animal != null) {
            animais.remove(animal);
        }
        else{
            throw new AnimalNaoExisteException();
        }
    }


    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public List<Animal> getAnimais() {
        return animais;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getTipoDePagamento() {
        return tipoDePagamento;
    }

    public void setTipoDePagamento(String tipoDePagamento) {
        this.tipoDePagamento = tipoDePagamento;
    }

    public boolean isPagou() {
        return pagou;
    }

    public void setPagou(boolean pagou) {
        this.pagou = pagou;
    }

    public boolean equals(Object obj){
        if(obj instanceof Cliente){
            Cliente cliente = (Cliente)obj;
            if(this.codigo == cliente.getCodigo()){
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return "Cliente{" + "codigo=" + codigo + ", nome=" + nome + ", telefone=" + telefone + ", animais=" + animais + ", tipoDePagamento=" + tipoDePagamento + ", pagou=" + pagou + '}';
    }
    
    
}
