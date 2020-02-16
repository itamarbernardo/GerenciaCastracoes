/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.gerenciacastracoes.negocio.dados;

import android.os.Environment;

import com.example.gerenciacastracoes.negocio.entidades.Cliente;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Itamar Jr
 */
public class RepositorioListaNegra implements IRepositorioListaNegra {

    private String nomeArquivo;

    public RepositorioListaNegra(){
        this.nomeArquivo = "listaNegra.dat";
    }

    @Override
    public void adicionarCliente(Cliente c) {

        File folder = new File(Environment.getExternalStorageDirectory() + "/Gerencia_Castracoes");
        if(!folder.exists()){
            folder.mkdir();
        }
        File arquivo = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), "/Gerencia_Castracoes/" + nomeArquivo);


        List<Cliente> clientes = null;

        try {
            FileInputStream file = new FileInputStream(arquivo);
            ObjectInputStream is = new ObjectInputStream(file);
            clientes = (List<Cliente>) is.readObject();
            is.close();
        } catch (FileNotFoundException ex) {
            clientes = new ArrayList<>();
            System.out.println(ex.getMessage());
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        } catch (ClassNotFoundException ex) {
            System.out.println(ex.getMessage());
        }

        clientes.add(c);
        //Escrita
        try {
            FileOutputStream file2 = new FileOutputStream(arquivo);
            ObjectOutputStream os = new ObjectOutputStream(file2);
            os.writeObject(clientes);
            os.close();
        } catch (FileNotFoundException ex) {
            System.out.println(ex.getMessage());
        } catch (IOException ex) {
            System.out.println(ex.getMessage());

        }

    }

    @Override
    public void removerCliente(Cliente c) {
        File folder = new File(Environment.getExternalStorageDirectory() + "/Gerencia_Castracoes");
        if(!folder.exists()){
            folder.mkdir();
        }
        File arquivo = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), "/Gerencia_Castracoes/" + nomeArquivo);

        List<Cliente> clientes = null;

        //Escrita
        try {
            FileInputStream file = new FileInputStream(arquivo);
            ObjectInputStream is = new ObjectInputStream(file);
            clientes = (List<Cliente>) is.readObject();
            is.close();

            clientes.remove(c);

            FileOutputStream file2 = new FileOutputStream(arquivo);
            ObjectOutputStream os = new ObjectOutputStream(file2);
            os.writeObject(clientes);
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
    public void alterarCliente(Cliente c) {
        File folder = new File(Environment.getExternalStorageDirectory() + "/Gerencia_Castracoes");
        if(!folder.exists()){
            folder.mkdir();
        }
        File arquivo = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), "/Gerencia_Castracoes/" + nomeArquivo);

        List<Cliente> clientes = null;

        try {
            FileInputStream file = new FileInputStream(arquivo);
            ObjectInputStream is = new ObjectInputStream(file);
            clientes = (List<Cliente>) is.readObject();
            is.close();
        } catch (FileNotFoundException ex) {
            System.out.println(ex.getMessage());
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        } catch (ClassNotFoundException ex) {
            System.out.println(ex.getMessage());
        }

        int index = clientes.indexOf(c);
        clientes.set(index, c);

        //Escrita
        try {
            FileOutputStream file2 = new FileOutputStream(arquivo);
            ObjectOutputStream os = new ObjectOutputStream(file2);
            os.writeObject(clientes);
            os.close();
        } catch (FileNotFoundException ex) {
            System.out.println(ex.getMessage());
        } catch (IOException ex) {
            System.out.println(ex.getMessage());

        }
    }

    @Override
    public Cliente procurarCliente(int codigo) {
        File folder = new File(Environment.getExternalStorageDirectory() + "/Gerencia_Castracoes");
        if(!folder.exists()){
            folder.mkdir();
        }
        File arquivo = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), "/Gerencia_Castracoes/" + nomeArquivo);

        List<Cliente> clientes = null;

        try {
            FileInputStream file = new FileInputStream(arquivo);
            ObjectInputStream is = new ObjectInputStream(file);
            clientes = (List<Cliente>) is.readObject();
            is.close();

        } catch (FileNotFoundException ex) {
            System.out.println(ex.getMessage());
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        } catch (ClassNotFoundException ex) {
            System.out.println(ex.getMessage());
        }

        if (clientes != null) {
            for (Cliente cliente : clientes) {
                if (cliente.getCodigo() == codigo) {
                    return cliente;
                }
            }
        }
        return null;

    }

    @Override
    public Cliente procurarCliente(String telefone) {
        File folder = new File(Environment.getExternalStorageDirectory() + "/Gerencia_Castracoes");
        if(!folder.exists()){
            folder.mkdir();
        }
        File arquivo = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), "/Gerencia_Castracoes/" + nomeArquivo);

        List<Cliente> clientes = null;

        try {
            FileInputStream file = new FileInputStream(arquivo);
            ObjectInputStream is = new ObjectInputStream(file);
            clientes = (List<Cliente>) is.readObject();
            is.close();

        } catch (FileNotFoundException ex) {
            System.out.println(ex.getMessage());
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        } catch (ClassNotFoundException ex) {
            System.out.println(ex.getMessage());
        }

        if (clientes != null) {
            for (Cliente cliente : clientes) {
                if (cliente.getTelefone().equals(telefone)) {
                    return cliente;
                }
            }
        }

        return null;
    }

    @Override
    public List<Cliente> listagemClientesListaNegra() {
        File folder = new File(Environment.getExternalStorageDirectory() + "/Gerencia_Castracoes");
        if(!folder.exists()){
            folder.mkdir();
        }
        File arquivo = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), "/Gerencia_Castracoes/" + nomeArquivo);

        List<Cliente> clientes = null;

        try {
            FileInputStream file = new FileInputStream(arquivo);
            ObjectInputStream is = new ObjectInputStream(file);
            clientes = (List<Cliente>) is.readObject();
            is.close();
        } catch (FileNotFoundException ex) {
            System.out.println(ex.getMessage());
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        } catch (ClassNotFoundException ex) {
            System.out.println(ex.getMessage());
        }

        return clientes;
    }

}
