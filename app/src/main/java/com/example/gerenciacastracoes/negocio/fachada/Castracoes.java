/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.gerenciacastracoes.negocio.fachada;

import com.example.gerenciacastracoes.negocio.NegocioListaNegra;
import com.example.gerenciacastracoes.negocio.NegocioMutirao;
import com.example.gerenciacastracoes.negocio.entidades.Animal;
import com.example.gerenciacastracoes.negocio.entidades.Cliente;
import com.example.gerenciacastracoes.negocio.entidades.Mutirao;
import com.example.gerenciacastracoes.negocio.exceccoes.Animal.AnimalJaAdicionadoException;
import com.example.gerenciacastracoes.negocio.exceccoes.Cliente.ClienteEstaNaListaNegraException;
import com.example.gerenciacastracoes.negocio.exceccoes.Cliente.ClienteJaAdicionadoException;
import com.example.gerenciacastracoes.negocio.exceccoes.Cliente.ClienteNaoExisteException;
import com.example.gerenciacastracoes.negocio.exceccoes.Cliente.ClienteNaoPossuiAnimalException;
import com.example.gerenciacastracoes.negocio.exceccoes.mutirao.JaExisteMutiraoComEssaDataException;
import com.example.gerenciacastracoes.negocio.exceccoes.mutirao.MutiraoJaExisteException;
import com.example.gerenciacastracoes.negocio.exceccoes.mutirao.MutiraoNaoExisteException;
import com.example.gerenciacastracoes.negocio.exceccoes.mutirao.TipoDeMutiraoIncompativelComAnimalException;

import java.time.LocalDate;
import java.util.List;

/**
 * @author Itamar Jr
 */
public class Castracoes {

    private static Castracoes fachada;

    private NegocioMutirao negocioMutirao;
    private NegocioListaNegra negocioListaNegra;

    public Castracoes() {
        this.negocioMutirao = new NegocioMutirao();
        this.negocioListaNegra = new NegocioListaNegra();
    }

    public static Castracoes getFachada() {
        if (fachada == null) {
            fachada = new Castracoes();
        }
        return fachada;
    }

    public int adicionarMutirao(LocalDate data, String tipo) throws MutiraoJaExisteException, JaExisteMutiraoComEssaDataException {
        int codigo;
        List<Mutirao> lista = negocioMutirao.listagemMutiroes();

        if (lista != null) {
            codigo = lista.size();
        } else {
            codigo = 0;
        }

        Mutirao m = negocioMutirao.buscarMutirao(data);
        if (m != null) {
            throw new JaExisteMutiraoComEssaDataException();
        }

        Mutirao mutirao = new Mutirao(codigo, data, tipo);
        negocioMutirao.adicionarMutirao(mutirao);

        return mutirao.getCodigo();
    }

    public Mutirao buscarMutirao(LocalDate data) {
        return negocioMutirao.buscarMutirao(data);
    }

    public Mutirao buscarMutirao(int codigo) {
        return negocioMutirao.buscarMutirao(codigo);
    }

    public void removerMutirao(int codigo) throws MutiraoNaoExisteException {
        negocioMutirao.removerMultirao(codigo);
    }

    public void removerMutirao(LocalDate data) throws MutiraoNaoExisteException {
        negocioMutirao.removerMultirao(data);
    }

    public void alterarMutirao(int codigo, LocalDate data, String tipo) throws MutiraoNaoExisteException, JaExisteMutiraoComEssaDataException {
        Mutirao m = negocioMutirao.buscarMutirao(codigo);
        Mutirao mData = negocioMutirao.buscarMutirao(data);
        if (mData == null) {
            if (m != null) {
                m.setData(data);
                m.setTipo(tipo);
                negocioMutirao.alterarMutirao(m);
            }
        } else {
            throw new JaExisteMutiraoComEssaDataException();
        }
    }

    public List<Mutirao> listagemMutiroes() {
        return negocioMutirao.listagemMutiroes();
    }


    public void adicionarCliente(LocalDate data, String nome, String telefone, String tipoDePagamento, boolean pagou, String nomeAnimal, String tipo, char sexo, String raca, String pelagem) throws AnimalJaAdicionadoException, ClienteNaoPossuiAnimalException, ClienteJaAdicionadoException, MutiraoNaoExisteException, ClienteEstaNaListaNegraException, TipoDeMutiraoIncompativelComAnimalException {
        int codigoCliente;
        int codigoAnimal;
        List<Cliente> listaClientes;
        List<Animal> listaAnimais;

        Mutirao mutirao = negocioMutirao.buscarMutirao(data);
        Animal animal;
        Cliente cliente;
        Cliente listaNegra = negocioListaNegra.buscarCliente(telefone);
        if (listaNegra == null) {
            if (mutirao != null) {
                if (mutirao.getTipo().equals(tipo) || mutirao.getTipo().equals("Misto")) {

                    listaClientes = mutirao.getClientes();
                    if (listaClientes != null) {
                        codigoCliente = listaClientes.size();
                    } else {
                        codigoCliente = 0;
                    }


                    cliente = new Cliente(codigoCliente, nome, telefone, tipoDePagamento, pagou);
                    listaAnimais = cliente.getAnimais();
                    if (listaAnimais != null) {
                        codigoAnimal = listaAnimais.size();
                    } else {
                        codigoAnimal = 0;
                    }
                    animal = new Animal(codigoAnimal, nomeAnimal, tipo, sexo, raca, pelagem);

                    cliente.adicionarAnimal(animal);

                    mutirao.adicionarCliente(cliente);

                    negocioMutirao.alterarMutirao(mutirao);
                } else {
                    throw new TipoDeMutiraoIncompativelComAnimalException(mutirao.getTipo());
                }
            } else {
                throw new MutiraoNaoExisteException();
            }
        } else {
            throw new ClienteEstaNaListaNegraException();
        }
    }



    public void adicionarCliente(int codigoMutirao, String nome, String telefone, String tipoDePagamento, boolean pagou, String nomeAnimal, String tipo, char sexo, String raca, String pelagem) throws AnimalJaAdicionadoException, ClienteNaoPossuiAnimalException, ClienteJaAdicionadoException, MutiraoNaoExisteException, ClienteEstaNaListaNegraException, TipoDeMutiraoIncompativelComAnimalException {
        int codigoCliente;
        int codigoAnimal;
        List<Cliente> listaClientes;
        List<Animal> listaAnimais;

        Mutirao mutirao = negocioMutirao.buscarMutirao(codigoMutirao);
        Animal animal;
        Cliente cliente;
        Cliente listaNegra = negocioListaNegra.buscarCliente(telefone);
        if (listaNegra == null) {
            if (mutirao != null) {
                if (mutirao.getTipo().equals(tipo) || mutirao.getTipo().equals("Misto")) {
                    listaClientes = mutirao.getClientes();
                    if (listaClientes != null) {
                        codigoCliente = listaClientes.size();
                    } else {
                        codigoCliente = 0;
                    }


                    cliente = new Cliente(codigoCliente, nome, telefone, tipoDePagamento, pagou);
                    listaAnimais = cliente.getAnimais();
                    if (listaAnimais != null) {
                        codigoAnimal = listaAnimais.size();
                    } else {
                        codigoAnimal = 0;
                    }
                    animal = new Animal(codigoAnimal, nomeAnimal, tipo, sexo, raca, pelagem);

                    cliente.adicionarAnimal(animal);

                    mutirao.adicionarCliente(cliente);

                    negocioMutirao.alterarMutirao(mutirao);
                } else {
                    throw new TipoDeMutiraoIncompativelComAnimalException(mutirao.getTipo());
                }
            } else {
                throw new MutiraoNaoExisteException();
            }
        } else {
            throw new ClienteEstaNaListaNegraException();
        }
    }


    public void adicionarAnimal(LocalDate dataMutirao, String telefone, String
            nomeAnimal, String tipo, char sexo, String raca, String pelagem) throws
            AnimalJaAdicionadoException, ClienteNaoExisteException, MutiraoNaoExisteException, TipoDeMutiraoIncompativelComAnimalException {
        int codigo;
        List<Animal> listaAnimais;

        Mutirao mutirao = negocioMutirao.buscarMutirao(dataMutirao);
        if (mutirao != null) {
            if (mutirao.getTipo().equals(tipo) || mutirao.getTipo().equals("Misto")) {
                Cliente c = mutirao.procurarCliente(telefone);
                if (c != null) {
                    listaAnimais = c.getAnimais();
                    if (listaAnimais != null) {
                        codigo = listaAnimais.size();
                    } else {
                        codigo = 0;
                    }

                    Animal animal = new Animal(codigo, nomeAnimal, tipo, sexo, raca, pelagem);
                    c.adicionarAnimal(animal);

                    negocioMutirao.alterarMutirao(mutirao);
                } else {
                    throw new ClienteNaoExisteException();
                }
            } else {
                throw new TipoDeMutiraoIncompativelComAnimalException(mutirao.getTipo());
            }

        } else {
            throw new MutiraoNaoExisteException();
        }
    }

    public void adicionarAnimal(int codigoMutirao, String telefone, String nomeAnimal, String
            tipo, char sexo, String raca, String pelagem) throws
            AnimalJaAdicionadoException, ClienteNaoExisteException, MutiraoNaoExisteException, TipoDeMutiraoIncompativelComAnimalException {
        int codigo;
        List<Animal> listaAnimais;

        Mutirao mutirao = negocioMutirao.buscarMutirao(codigoMutirao);
        if (mutirao != null) {
            if (mutirao.getTipo().equals(tipo) || mutirao.getTipo().equals("Misto")) {
                Cliente c = mutirao.procurarCliente(telefone);
                if (c != null) {
                    listaAnimais = c.getAnimais();
                    if (listaAnimais != null) {
                        codigo = listaAnimais.size();
                    } else {
                        codigo = 0;
                    }

                    Animal animal = new Animal(codigo, nomeAnimal, tipo, sexo, raca, pelagem);
                    c.adicionarAnimal(animal);

                    negocioMutirao.alterarMutirao(mutirao);
                } else {
                    throw new ClienteNaoExisteException();
                }
            } else {
                throw new TipoDeMutiraoIncompativelComAnimalException(mutirao.getTipo());
            }

        } else {
            throw new MutiraoNaoExisteException();
        }
    }


    public void adicionarClienteAListaNegra(LocalDate data, String telefone) throws
            ClienteNaoExisteException, MutiraoNaoExisteException, ClienteJaAdicionadoException {
        Mutirao mutirao = negocioMutirao.buscarMutirao(data);

        if (mutirao != null) {
            Cliente c = mutirao.procurarCliente(telefone);
            if (c != null) {
                mutirao.removerCliente(c);
                negocioMutirao.alterarMutirao(mutirao);

                negocioListaNegra.adicionarCliente(c);
            } else {
                throw new ClienteNaoExisteException();
            }
        } else {
            throw new MutiraoNaoExisteException();
        }
    }

    public void adicionarClienteAListaNegra(String nome, String telefone, String
            tipoDePagamento) throws ClienteJaAdicionadoException {
        int codigoCliente;
        List<Cliente> listaNegraClientes = negocioListaNegra.listagemClientesListaNegra();

        Cliente c = negocioListaNegra.buscarCliente(telefone);

        if (c == null) {
            if (listaNegraClientes != null) {
                codigoCliente = listaNegraClientes.size();
            } else {
                codigoCliente = 0;
            }
            Cliente cliente = new Cliente(codigoCliente, nome, telefone, tipoDePagamento);
            negocioListaNegra.adicionarCliente(cliente);
        } else {
            throw new ClienteJaAdicionadoException();
        }

    }

    public void removerClienteDaListaNegra(String telefone) throws ClienteNaoExisteException {
        negocioListaNegra.removerCliente(telefone);
    }

    public void removerClienteDaListaNegra(int codigo) throws ClienteNaoExisteException {
        negocioListaNegra.removerCliente(codigo);
    }

    public Cliente buscarClienteListaNegra(String telefone) {
        return negocioListaNegra.buscarCliente(telefone);
    }

    public Cliente buscarClienteListaNegra(int codigo) {
        return negocioListaNegra.buscarCliente(codigo);
    }

    public List<Cliente> listagemClientesListaNegra() {
        return negocioListaNegra.listagemClientesListaNegra();
    }

}
