/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.example.gerenciacastracoes.negocio.entidades;

import java.io.Serializable;

/**
 *
 * @author Itamar Jr
 */
public class Animal implements Serializable{
    
    private static int totalAnimais;
    private int codigo;
    private String nome;
    private String tipo; //Cachorro ou gato
    private char sexo;
    private String raca;
    private String pelagem; //Cor do animal

    public Animal(String nome, String tipo, char sexo, String raca, String pelagem) {
        this.nome = nome;
        this.tipo = tipo;
        this.sexo = sexo;
        this.raca = raca;
        this.pelagem = pelagem;
        this.codigo = totalAnimais;
        totalAnimais++;
    }

    
    
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public char getSexo() {
        return sexo;
    }

    public void setSexo(char sexo) {
        this.sexo = sexo;
    }

    public String getRaca() {
        return raca;
    }

    public void setRaca(String raca) {
        this.raca = raca;
    }

    public String getPelagem() {
        return pelagem;
    }

    public void setPelagem(String pelagem) {
        this.pelagem = pelagem;
    }

    public static int getTotalAnimais() {
        return totalAnimais;
    }

    public int getCodigo() {
        return codigo;
    }
    
    @Override
    public boolean equals(Object obj){
        if(obj instanceof Animal){
            Animal animal = (Animal)obj;
            if(this.codigo == animal.getCodigo()){
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return "Animal{" + "codigo=" + codigo + ", nome=" + nome + ", tipo=" + tipo + ", sexo=" + sexo + ", raca=" + raca + ", pelagem=" + pelagem + '}';
    }
    
    
}
