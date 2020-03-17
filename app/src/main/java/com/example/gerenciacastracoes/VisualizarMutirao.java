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

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class VisualizarMutirao extends AppCompatActivity {

    private Castracoes fachada = Castracoes.getFachada();
    private List<Cliente> listGroup;
    //private HashMap<Cliente, List<Animal>> listData;

    private List<Cliente> listGroupEspera;
    //private HashMap<Cliente, List<Animal>> listDataEspera;

    private List<Cliente> listGroupNaoPagaram;
    //private HashMap<Cliente, List<Animal>> listDataClientesNaoPagaram;

    private List<Cliente> listGroupPagaram;
    //private HashMap<Cliente, List<Animal>> listDataClientesPagaram;

    private static int codigoMutirao;
    private Mutirao mutirao;

    private TextView dataMutirao;
    private TextView quantidadeAnimais;
    private TextView quantidadeMachos;
    private TextView quantidadeFemeas;
    private TextView quantidadeRoupinhas;
    private ImageView imagem;
    private ExpandableListView expandableListaClientes;
    private ExpandableListView expandableListaEspera;
    private Toolbar toolbar;
    private Spinner spinnerSelecaoClientes;
    private ArrayAdapter adapter;
    private int codigoSelecaoCliente;

    private ExpandableAdapter expandableAdapter;
    private EditText edtTxtPesquisa;
    private EditText edtTxtPesquisaListaEspera;
    private TextView txtTitulo;
    private TextView txtTituloListaEspera;
    private boolean verificaCliqueBotao;
    private boolean verificaCliqueBotaoListaEspera;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visualizar_mutirao);

        inicializaToolbar();

        Intent intentRecebeParametos = getIntent();
        Bundle parametros = intentRecebeParametos.getExtras();

        if (parametros != null) {
            codigoMutirao = parametros.getInt("codigo_mutirao");
        }

        mutirao = fachada.buscarMutirao(codigoMutirao);
        if (mutirao != null) {

            inicializaElementos();
            preencherDadosCabecalhoMutirao();

            //buildListClientes();
            buildListSelecaoClientes();
            configurarExpandableListaClientes();

            buildListClientesEspera();
            configurarExpandableListaEspera();

        } else {
            Toast.makeText(VisualizarMutirao.this, "Mutirão não encontrado!", Toast.LENGTH_SHORT).show();
        }

    }

    public void inicializaToolbar(){
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    public void inicializaElementos(){

        dataMutirao = (TextView) findViewById(R.id.txtData);
        quantidadeAnimais = (TextView) findViewById(R.id.txtQuantidadeAnimais);
        quantidadeMachos = (TextView) findViewById(R.id.txtQuantidadeMachos);
        quantidadeFemeas = (TextView) findViewById(R.id.txtQuantidadeFemeas);
        imagem = (ImageView) findViewById(R.id.imageView);
        quantidadeRoupinhas = (TextView) findViewById(R.id.txtQuantidadeRoupinhas);
        expandableListaClientes = (ExpandableListView) findViewById(R.id.expandableListaClientes);
        expandableListaEspera = (ExpandableListView) findViewById(R.id.expandableListaEspera);

        spinnerSelecaoClientes = (Spinner) findViewById(R.id.spinnerSelecaoClientes);
        adapter = ArrayAdapter.createFromResource(this, R.array.selecao_clientes, android.R.layout.simple_list_item_1);
        spinnerSelecaoClientes.setAdapter(adapter);

        edtTxtPesquisa = (EditText) findViewById(R.id.edtTxtPesquisa);
        edtTxtPesquisaListaEspera = (EditText) findViewById(R.id.edtTxtPesquisaListaEspera);
        txtTitulo = (TextView) findViewById(R.id.textView5);
        txtTituloListaEspera = (TextView) findViewById(R.id.textView6);
        verificaCliqueBotao = false;
        verificaCliqueBotaoListaEspera = false;
    }

    public void irTelaCadastrarCliente(View view) {
        Intent intent = new Intent(getApplicationContext(), CadastrarCliente.class);
        Bundle parametros = new Bundle();

        parametros.putInt("codigo_mutirao", codigoMutirao);
        intent.putExtras(parametros);
        startActivity(intent);
    }

    public void aparecerCampoPesquisa(View view){
        if(verificaCliqueBotao == false) {
            edtTxtPesquisa.setVisibility(View.VISIBLE);
            txtTitulo.setVisibility(View.INVISIBLE);
            verificaCliqueBotao = true;
        }else{
            edtTxtPesquisa.setVisibility(View.INVISIBLE);
            edtTxtPesquisa.setText("");
            txtTitulo.setVisibility(View.VISIBLE);
            verificaCliqueBotao = false;
        }
    }

    public void aparecerCampoPesquisaListaEspera(View view){
        if(verificaCliqueBotaoListaEspera == false) {
            edtTxtPesquisaListaEspera.setVisibility(View.VISIBLE);
            txtTituloListaEspera.setVisibility(View.INVISIBLE);
            verificaCliqueBotaoListaEspera = true;
        }else{
            edtTxtPesquisaListaEspera.setVisibility(View.INVISIBLE);
            edtTxtPesquisaListaEspera.setText("");
            txtTituloListaEspera.setVisibility(View.VISIBLE);
            verificaCliqueBotaoListaEspera = false;
        }
    }

    public void irTelaCadastrarClienteListaEspera(View view) {
        Intent intent = new Intent(getApplicationContext(), CadastroClienteListaEspera.class);
        Bundle parametros = new Bundle();

        parametros.putInt("codigo_mutirao", codigoMutirao);
        intent.putExtras(parametros);
        startActivity(intent);
    }

    //Expande todos automaticamente
    /*
    private void expandAll() {
        int count = expandableAdapter.getGroupCount();
        Log.d("Aviso", "Quantidade de elementos: " + count);

        for (int i = 0; i < count; i++){
            expandableListaClientes.expandGroup(i);
        }
    }
     */

    public void configurarExpandableListaClientes() {

        spinnerSelecaoClientes.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (spinnerSelecaoClientes.getSelectedItem().toString().equals("Pagaram")) {
                    expandableAdapter = new ExpandableAdapter(VisualizarMutirao.this, listGroupPagaram);
                    expandableListaClientes.setAdapter(expandableAdapter);
                    codigoSelecaoCliente = 1;
                } else if (spinnerSelecaoClientes.getSelectedItem().toString().equals("Não Pagaram")){
                    expandableAdapter = new ExpandableAdapter(VisualizarMutirao.this, listGroupNaoPagaram);
                    expandableListaClientes.setAdapter(expandableAdapter);
                    codigoSelecaoCliente = 2;
                }else {
                    expandableAdapter = new ExpandableAdapter(VisualizarMutirao.this, listGroup);
                    //expandableListaClientes.setAdapter(new ExpandableAdapter(VisualizarMutirao.this, listGroup));
                    expandableListaClientes.setAdapter(expandableAdapter);
                    codigoSelecaoCliente = 0;

                }

                //-----------------------------------------------------------
                edtTxtPesquisa.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        expandableAdapter.filterData(s.toString());

                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



        expandableListaClientes.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                if(codigoSelecaoCliente == 1) {
                    irTelaVisualizarCliente(v, listGroupPagaram.get(groupPosition).getCodigo());
                    //Toast.makeText(getApplicationContext(), listGroup.get(groupPosition).getCodigo()+"", Toast.LENGTH_LONG).show();
                    //Quando clicar no animal, aí vai para o Visualizar Cliente com o ListView de animais.
                }
                else if(codigoSelecaoCliente == 2){
                    irTelaVisualizarCliente(v, listGroupNaoPagaram.get(groupPosition).getCodigo());
                }
                else{
                    irTelaVisualizarCliente(v, listGroup.get(groupPosition).getCodigo());
                }
                    return false;
            }
        });



    }



    public void configurarExpandableListaEspera() {
        final ExpandableAdapter adapterListaEspera = new ExpandableAdapter(VisualizarMutirao.this, listGroupEspera);

        expandableListaEspera.setAdapter(adapterListaEspera);

        expandableListaEspera.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                irTelaVisualizarClienteListaEspera(v, listGroupEspera.get(groupPosition).getCodigo());
                //Toast.makeText(getApplicationContext(), listGroupEspera.get(groupPosition).getCodigo()+"", Toast.LENGTH_LONG).show();
                return false;
            }
        });

        edtTxtPesquisaListaEspera.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                adapterListaEspera.filterData(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

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

        int[] quantidadeAnimaisSexo = mutirao.calculaQuantAnimaisMachosEFemeas();

        dataMutirao.setText(data);
        quantidadeAnimais.setText(contAnimais + "");
        quantidadeMachos.setText(quantidadeAnimaisSexo[0] + "");
        quantidadeFemeas.setText(quantidadeAnimaisSexo[1] + "");
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

    public void buildListSelecaoClientes() {
        /**
        listData = new HashMap<Cliente, List<Animal>>();
        listDataClientesNaoPagaram = new HashMap<Cliente, List<Animal>>();
        listDataClientesPagaram = new HashMap<Cliente, List<Animal>>();
        */

        // GROUP
        listGroup = mutirao.getClientes(); //Pega todos os clientes
        listGroupNaoPagaram = new ArrayList<>();
        listGroupPagaram = new ArrayList<>();

        for(Cliente cliente : mutirao.getClientes()){
            if(cliente.isPagou()){
                listGroupPagaram.add(cliente);
            }else{
                listGroupNaoPagaram.add(cliente);
            }
        }


        /**
        // CHILDREN
        List<Animal> auxList;
        //Insere todos os clientes
        int cont = 0;
        for (Cliente cliente : listGroup) {
            auxList = cliente.getAnimais();
            listData.put(listGroup.get(cont), auxList);
            cont++;
        }

        //Insere os clientes que pagaram
        cont = 0;
        for (Cliente cliente : listGroupPagaram) {
            auxList = cliente.getAnimais();
            listDataClientesPagaram.put(listGroupPagaram.get(cont), auxList);
            cont++;
        }

        //Insere os clientes que não pagaram
        cont = 0;
        for (Cliente cliente : listGroupNaoPagaram) {
            auxList = cliente.getAnimais();
            listDataClientesNaoPagaram.put(listGroupNaoPagaram.get(cont), auxList);
            cont++;
        }

         */

    }

    public void buildListClientesEspera() {
        //listGroupEspera = new ArrayList<Cliente>();
        //listDataEspera = new HashMap<Cliente, List<Animal>>();

        // GROUP
        listGroupEspera = mutirao.getListaEspera();

        // CHILDREN
        List<Animal> auxList;
        /**
        int cont = 0;
        for (Cliente cliente : listGroupEspera) {
            auxList = cliente.getAnimais();
            listDataEspera.put(listGroupEspera.get(cont), auxList);
            cont++;
        }
        */


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

    public void irTelaVisualizarClienteListaEspera(View view, int codigoCliente){
        Intent intent = new Intent(getApplicationContext(), VisualizarClienteListaEspera.class);
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
