/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.gerenciacastracoes.negocio.entidades;

import com.example.gerenciacastracoes.negocio.exceccoes.Cliente.ClienteJaAdicionadoException;
import com.example.gerenciacastracoes.negocio.exceccoes.Cliente.ClienteNaoExisteException;
import com.example.gerenciacastracoes.negocio.exceccoes.Cliente.ClienteNaoPossuiAnimalException;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Itamar Jr
 */
public class Mutirao implements Serializable {

    private int codigo;
    private LocalDate data;
    private List<Cliente> clientes;
    private List<Cliente> listaEspera;
    private String tipo; //Só gato, só cachorro ou misto.

    public Mutirao(int codigo, LocalDate data, String tipo) {
        this.data = data;
        this.clientes = new ArrayList<>();
        this.listaEspera = new ArrayList<>();
        this.codigo = codigo;
        this.tipo = tipo;
    }

    public void transferirCliente(String telefone) throws ClienteNaoExisteException {
        Cliente c = procurarClienteListaEspera(telefone);
        if (c != null) {
            clientes.add(c);
            listaEspera.remove(c);
        } else {
            throw new ClienteNaoExisteException();
        }
    }

    public void transferirCliente(int codigo) throws ClienteNaoExisteException {
        Cliente c = procurarClienteListaEspera(codigo);
        if (c != null) {
            clientes.add(c);
            listaEspera.remove(c);
        } else {
            throw new ClienteNaoExisteException();
        }
    }

    public void adicionarCliente(Cliente c) throws ClienteNaoPossuiAnimalException, ClienteJaAdicionadoException {
        if (clientes.indexOf(c) == -1) {
            if (c.getAnimais().size() > 0) {
                clientes.add(c);
            } else {
                throw new ClienteNaoPossuiAnimalException();
            }
        } else {
            throw new ClienteJaAdicionadoException();
        }

    }

    public Cliente procurarCliente(String telefone) {
        Cliente c = null;
        for (Cliente cliente : clientes) {
            if (cliente.getTelefone().equals(telefone)) {
                return cliente;
            }
        }
        return c;
    }

    public Cliente procurarCliente(int codigo) {
        Cliente c = null;
        for (Cliente cliente : clientes) {
            if (cliente.getCodigo() == codigo) {
                return cliente;
            }
        }
        return c;
    }

    public void alterarCliente(Cliente c) throws ClienteNaoExisteException {
        int index = clientes.indexOf(c);
        if (index != -1) {
            clientes.set(index, c);
        } else {
            throw new ClienteNaoExisteException();
        }
    }

    public void removerCliente(int codigoCliente) throws ClienteNaoExisteException {
        Cliente cliente = procurarCliente(codigoCliente);

        if (cliente != null) {
            clientes.remove(cliente);
        } else {
            throw new ClienteNaoExisteException();
        }
    }

    public void adicionarClienteListaEspera(Cliente c) throws ClienteNaoPossuiAnimalException, ClienteJaAdicionadoException {
        if (listaEspera.indexOf(c) == -1) {
            if (c.getAnimais().size() > 0) {
                listaEspera.add(c);
            } else {
                throw new ClienteNaoPossuiAnimalException();
            }
        } else {
            throw new ClienteJaAdicionadoException();
        }

    }

    public void alterarClienteListaEspera(Cliente c) throws ClienteNaoExisteException {
        int index = listaEspera.indexOf(c);

        if (index != -1) {
            listaEspera.set(index, c);
        } else {
            throw new ClienteNaoExisteException();
        }
    }

    public Cliente procurarClienteListaEspera(String telefone) {
        Cliente c = null;
        for (Cliente cliente : listaEspera) {
            if (cliente.getTelefone().equals(telefone)) {
                return cliente;
            }
        }
        return c;
    }

    public Cliente procurarClienteListaEspera(int codigo) {
        Cliente c = null;
        for (Cliente cliente : listaEspera) {
            if (cliente.getCodigo() == codigo) {
                return cliente;
            }
        }
        return c;
    }

    public void removerClienteListaEspera(int codigoCliente) throws ClienteNaoExisteException {
        Cliente clienteListaEspera = procurarClienteListaEspera(codigoCliente);
        if (clienteListaEspera != null) {
            listaEspera.remove(clienteListaEspera);
        } else {
            throw new ClienteNaoExisteException();
        }
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public int getCodigo() {
        return codigo;
    }

    public List<Cliente> getClientes() {
        return clientes;
    }

    public List<Cliente> getListaEspera() {
        return listaEspera;
    }

    public boolean equals(Object obj) {
        if (obj instanceof Mutirao) {
            Mutirao mutirao = (Mutirao) obj;
            if (this.data.isEqual(mutirao.getData()) || this.codigo == mutirao.getCodigo()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return "Mutirao{" +
                "codigo=" + codigo +
                ", data=" + data +
                ", clientes=" + clientes +
                ", listaEspera=" + listaEspera +
                ", tipo='" + tipo + '\'' +
                '}';
    }
}
