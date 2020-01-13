/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.gerenciacastracoes.negocio.dados;

import android.content.Context;
import android.os.Environment;

import com.example.gerenciacastracoes.negocio.entidades.Mutirao;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Itamar Jr
 */
public class RepositorioMutirao implements IRepositorioMutirao {

    private String nomeArquivo = "mutiroes.dat";

    @Override
    public void adicionarMutirao(Mutirao m) {
        File folder = new File(Environment.getExternalStorageDirectory() + "/Gerencia_Castracoes");
        if(!folder.exists()){
            folder.mkdir();
        }
        File arquivo = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), "/Gerencia_Castracoes/" + nomeArquivo);

        List<Mutirao> mutiroes = null;

        try {
            FileInputStream file = new FileInputStream(arquivo);
            ObjectInputStream is = new ObjectInputStream(file);
            mutiroes = (List<Mutirao>) is.readObject();
            is.close();
        } catch (FileNotFoundException ex) {
            mutiroes = new ArrayList<>();
            System.out.println(ex.getMessage());
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        } catch (ClassNotFoundException ex) {
            System.out.println(ex.getMessage());
        }

        mutiroes.add(m);

        //Escrita
        try {
            FileOutputStream file2 = new FileOutputStream(arquivo);
            ObjectOutputStream os = new ObjectOutputStream(file2);
            os.writeObject(mutiroes);
            os.close();
        } catch (FileNotFoundException ex) {
            System.out.println(ex.getMessage());
        } catch (IOException ex) {
            System.out.println(ex.getMessage());

        }

    }

    @Override
    public Mutirao procurarMutirao(int codigo) {
        File folder = new File(Environment.getExternalStorageDirectory() + "/Gerencia_Castracoes");
        if(!folder.exists()){
            folder.mkdir();
        }
        File arquivo = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), "/Gerencia_Castracoes/" + nomeArquivo);

        List<Mutirao> mutiroes = null;

        try {
            FileInputStream file = new FileInputStream(arquivo);
            ObjectInputStream is = new ObjectInputStream(file);
            mutiroes = (List<Mutirao>) is.readObject();
            is.close();

        } catch (FileNotFoundException ex) {
            System.out.println(ex.getMessage());
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        } catch (ClassNotFoundException ex) {
            System.out.println(ex.getMessage());
        }

        //System.out.println("Teste: " + mutiroes);
        if (mutiroes != null) {
            for (Mutirao multirao : mutiroes) {
                if (multirao.getCodigo() == codigo) {
                    return multirao;
                }
            }
        }
        return null; //Se não achou o multirao

    }

    @Override
    public Mutirao procurarMutirao(LocalDate data) {
        File folder = new File(Environment.getExternalStorageDirectory() + "/Gerencia_Castracoes");
        if(!folder.exists()){
            folder.mkdir();
        }
        File arquivo = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), "/Gerencia_Castracoes/" + nomeArquivo);

        //Não precisa de Exception
        List<Mutirao> mutiroes = null;

        try {
            FileInputStream file = new FileInputStream(arquivo);
            ObjectInputStream is = new ObjectInputStream(file);
            mutiroes = (List<Mutirao>) is.readObject();
            is.close();

        } catch (FileNotFoundException ex) {
            System.out.println(ex.getMessage());
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        } catch (ClassNotFoundException ex) {
            System.out.println(ex.getMessage());
        }

        //System.out.println("Teste: " + mutiroes);
        if (mutiroes != null) {
            for (Mutirao multirao : mutiroes) {
                if (multirao.getData().isEqual(data)) {
                    return multirao;
                }
            }
        }

        return null; //Se não achou o multirao

    }

    @Override
    public void removerMutirao(Mutirao m) {
        File folder = new File(Environment.getExternalStorageDirectory() + "/Gerencia_Castracoes");
        if(!folder.exists()){
            folder.mkdir();
        }
        File arquivo = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), "/Gerencia_Castracoes/" + nomeArquivo);

        List<Mutirao> mutiroes = null;

        //Escrita
        try {
            FileInputStream file = new FileInputStream(arquivo);
            ObjectInputStream is = new ObjectInputStream(file);
            mutiroes = (List<Mutirao>) is.readObject();
            is.close();

            mutiroes.remove(m);

            FileOutputStream file2 = new FileOutputStream(arquivo);
            ObjectOutputStream os = new ObjectOutputStream(file2);
            os.writeObject(mutiroes);
            os.close();

        } catch (FileNotFoundException ex) {
            System.out.println(ex.getMessage());
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        } catch (ClassNotFoundException ex) {
            System.out.println(ex.getMessage());

        }

    }

    @Override
    public void alterarMutirao(Mutirao m) {
        File folder = new File(Environment.getExternalStorageDirectory() + "/Gerencia_Castracoes");
        if(!folder.exists()){
            folder.mkdir();
        }
        File arquivo = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), "/Gerencia_Castracoes/" + nomeArquivo);

        List<Mutirao> mutiroes = null;

        try {
            FileInputStream file = new FileInputStream(arquivo);
            ObjectInputStream is = new ObjectInputStream(file);
            mutiroes = (List<Mutirao>) is.readObject();
            is.close();
        } catch (FileNotFoundException ex) {
            System.out.println(ex.getMessage());
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        } catch (ClassNotFoundException ex) {
            System.out.println(ex.getMessage());
        }

        int index = mutiroes.indexOf(m);
        mutiroes.set(index, m);

        //Escrita
        try {
            FileOutputStream file2 = new FileOutputStream(arquivo);
            ObjectOutputStream os = new ObjectOutputStream(file2);
            os.writeObject(mutiroes);
            os.close();
        } catch (FileNotFoundException ex) {
            System.out.println(ex.getMessage());
        } catch (IOException ex) {
            System.out.println(ex.getMessage());

        }

    }

    @Override
    public List<Mutirao> listagemMutiroes() {
        File folder = new File(Environment.getExternalStorageDirectory() + "/Gerencia_Castracoes");
        if(!folder.exists()){
            folder.mkdir();
        }
        File arquivo = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), "/Gerencia_Castracoes/" + nomeArquivo);

        List<Mutirao> mutiroes = null;

        try {
            FileInputStream file = new FileInputStream(arquivo);
            ObjectInputStream is = new ObjectInputStream(file);
            mutiroes = (List<Mutirao>) is.readObject();
            is.close();
        } catch (FileNotFoundException ex) {
            System.out.println(ex.getMessage());
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        } catch (ClassNotFoundException ex) {
            System.out.println(ex.getMessage());
        }

        return mutiroes;
    }

}



