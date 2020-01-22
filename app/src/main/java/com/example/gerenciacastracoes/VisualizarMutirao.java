package com.example.gerenciacastracoes;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.example.gerenciacastracoes.negocio.entidades.Animal;
import com.example.gerenciacastracoes.negocio.entidades.Cliente;
import com.example.gerenciacastracoes.negocio.entidades.Mutirao;
import com.example.gerenciacastracoes.negocio.exceccoes.mutirao.MutiraoNaoExisteException;
import com.example.gerenciacastracoes.negocio.fachada.Castracoes;
import com.example.gerenciacastracoes.ui.main.ExpandableAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class VisualizarMutirao extends AppCompatActivity {

    private Castracoes fachada = Castracoes.getFachada();
    private List<Cliente> listGroup;
    private HashMap<Cliente, List<Animal>> listData;

    private List<Cliente> listGroupEspera;
    private HashMap<Cliente, List<Animal>> listDataEspera;

    private static int codigoMutirao;
    private Mutirao mutirao;

    private TextView dataMutirao;
    private TextView codigo;
    private TextView quantidadeAnimais;
    private TextView quantidadeListaEspera;
    private TextView quantidadeRoupinhas;
    private ImageView imagem;
    private ExpandableListView expandableListaClientes;
    private ExpandableListView expandableListaEspera;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visualizar_mutirao);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intentRecebeParametos = getIntent();
        Bundle parametros = intentRecebeParametos.getExtras();

        if (parametros != null) {
            codigoMutirao = parametros.getInt("codigo_mutirao");
        }

        mutirao = fachada.buscarMutirao(codigoMutirao);
        if (mutirao != null) {

            dataMutirao = (TextView) findViewById(R.id.txtData);
            codigo = (TextView) findViewById(R.id.txtCodigo);
            quantidadeAnimais = (TextView) findViewById(R.id.txtQuantidadeAnimais);
            quantidadeListaEspera = (TextView) findViewById(R.id.txtQntListaEspera);
            imagem = (ImageView) findViewById(R.id.imageView);
            quantidadeRoupinhas = (TextView) findViewById(R.id.txtQuantidadeRoupinhas);

            preencherDadosCabecalhoMutirao();

            buildListClientes();
            expandableListaClientes = (ExpandableListView) findViewById(R.id.expandableListaClientes);
            configurarExpandableListaClientes();

            buildListClientesEspera();
            expandableListaEspera = (ExpandableListView) findViewById(R.id.expandableListaEspera);
            configurarExpandableListaEspera();

        } else {
            Toast.makeText(VisualizarMutirao.this, "Mutirão não encontrado!", Toast.LENGTH_SHORT).show();
        }

    }

    public void irTelaCadastrarCliente(View view) {
        Intent intent = new Intent(getApplicationContext(), CadastrarCliente.class);
        Bundle parametros = new Bundle();

        parametros.putInt("codigo_mutirao", codigoMutirao);
        intent.putExtras(parametros);
        startActivity(intent);
        //finish();
    }

    public void configurarExpandableListaClientes() {
        expandableListaClientes.setAdapter(new ExpandableAdapter(VisualizarMutirao.this, listGroup, listData));

        expandableListaClientes.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                //Toast.makeText(VisualizarMutirao.this, "Group: " + groupPosition + "| Item: " + childPosition, Toast.LENGTH_SHORT).show();
                //Toast.makeText(VisualizarMutirao.this, "Cliente:" + listGroup.get(groupPosition).toString(), Toast.LENGTH_SHORT).show();
                irTelaVisualizarCliente(v, listGroup.get(groupPosition).getCodigo());
                //Quando clicar no animal, aí vai para o Visualizar Cliente com o ListView de animais.
                return false;
            }
        });



        expandableListaClientes.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {
                Toast.makeText(VisualizarMutirao.this, "Group (Expand): " + groupPosition, Toast.LENGTH_SHORT).show();

            }
        });

        expandableListaClientes.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {
            @Override
            public void onGroupCollapse(int groupPosition) {

                Toast.makeText(VisualizarMutirao.this, "Group (Collapse): " + groupPosition, Toast.LENGTH_SHORT).show();
            }
        });


    }

    public void configurarExpandableListaEspera() {
        expandableListaEspera.setAdapter(new ExpandableAdapter(VisualizarMutirao.this, listGroupEspera, listDataEspera));

        expandableListaEspera.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                //Toast.makeText(VisualizarMutirao.this, "Group: " + groupPosition + "| Item: " + childPosition, Toast.LENGTH_SHORT).show();
                //Toast.makeText(VisualizarMutirao.this, "Cliente:" + listGroup.get(groupPosition).toString(), Toast.LENGTH_SHORT).show();
                irTelaVisualizarCliente(v, listGroup.get(groupPosition).getCodigo());
                //Verifica se é necessário criar um outro VisualizarClienteListaEspera
                //Quando clicar no animal, aí vai para o Visualizar Cliente com o ListView de animais.
                return false;
            }
        });



        expandableListaEspera.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {
                Toast.makeText(VisualizarMutirao.this, "Group (Expand): " + groupPosition, Toast.LENGTH_SHORT).show();

            }
        });

        expandableListaEspera.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {
            @Override
            public void onGroupCollapse(int groupPosition) {

                Toast.makeText(VisualizarMutirao.this, "Group (Collapse): " + groupPosition, Toast.LENGTH_SHORT).show();
            }
        });


    }

    public void preencherDadosCabecalhoMutirao() {
        int contAnimais = 0;
        int contRoupinhas = 0;

        List<Cliente> clientes = mutirao.getClientes();
        if (clientes != null && clientes.size() != 0) {
            for (Cliente c : clientes) {
                contAnimais = contAnimais + c.getAnimais().size();

                for(Animal animal : c.getAnimais()){
                    if(animal.isQuerRoupinha()){
                        contRoupinhas++;
                    }
                }

            }
        }

        int contListaEspera = 0;
        List<Cliente> listaEspera = mutirao.getListaEspera();
        if (listaEspera != null && listaEspera.size() != 0) {
            for (Cliente c : listaEspera) {
                contListaEspera = contListaEspera + c.getAnimais().size();
            }
        }
        String data = ClasseUtilitaria.converterDataParaString(mutirao.getData()); //Tenho que formatar a data para mostrar.

        dataMutirao.setText(data);
        codigo.setText(mutirao.getCodigo() + "");
        quantidadeAnimais.setText(contAnimais + "");
        quantidadeListaEspera.setText(contListaEspera + "");
        quantidadeRoupinhas.setText(contRoupinhas + "");

        String tipo = mutirao.getTipo();
        if (tipo.equals("Gato")) {
            imagem.setImageResource(R.drawable.gato);
        } else if (tipo.equals("Cachorro")) {
            imagem.setImageResource(R.drawable.cachorro2);
        } else {
            imagem.setImageResource(R.drawable.misto);
        }
    }

    public void buildListClientes() {
        //listGroup = new ArrayList<Cliente>();
        listData = new HashMap<Cliente, List<Animal>>();

        // GROUP
        listGroup = mutirao.getClientes();

        // CHILDREN
        List<Animal> auxList;
        int cont = 0;
        for (Cliente cliente : listGroup) {
            auxList = cliente.getAnimais();
            listData.put(listGroup.get(cont), auxList);
            cont++;
        }


    }

    public void buildListClientesEspera() {
        //listGroupEspera = new ArrayList<Cliente>();
        listDataEspera = new HashMap<Cliente, List<Animal>>();

        // GROUP
        listGroupEspera = mutirao.getListaEspera();

        // CHILDREN
        List<Animal> auxList;
        int cont = 0;
        for (Cliente cliente : listGroupEspera) {
            auxList = cliente.getAnimais();
            listDataEspera.put(listGroupEspera.get(cont), auxList);
            cont++;
        }



    }

    public void irTelaEditarMutirao(View view){
        Intent intent = new Intent(getApplicationContext(), EditarMutirao.class);
        Bundle parametos = new Bundle();
        parametos.putInt("codigo_mutirao", codigoMutirao);

        intent.putExtras(parametos);

        startActivity(intent);
    }

    public void irTelaVisualizarCliente(View view, int codigoCliente){
        Intent intent = new Intent(getApplicationContext(), VisualizarCliente.class);
        Bundle parametos = new Bundle();
        parametos.putInt("codigo_mutirao", codigoMutirao);
        parametos.putInt("codigo_cliente", codigoCliente);

        intent.putExtras(parametos);

        startActivity(intent);
    }

    public void removerMutirao(View view){
        AlertDialog.Builder alerta = new AlertDialog.Builder(this);
        alerta.setTitle("Remover...");
        alerta.setCancelable(false); //Se tiver true, permite que a caixa de dialogo suma se clicar fora da caixa de texto.
        alerta.setIcon(R.mipmap.ic_delete);
        alerta.setMessage("Tem certeza que deseja excluir este mutirão?");
        alerta.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                try {
                    fachada.removerMutirao(codigoMutirao);
                    ClasseUtilitaria.emitirAlerta(VisualizarMutirao.this, "Mutirão excluído com sucesso!");
                    irTelaListagemMutiroes();

                } catch (MutiraoNaoExisteException e) {
                    Toast.makeText(VisualizarMutirao.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }
        });
        alerta.setNegativeButton("Não", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        alerta.show();

    }

    public void irTelaListagemMutiroes(){
        Intent intent = new Intent(getApplicationContext(), ListagemMutiroes.class);
        startActivity(intent);
    }

}
