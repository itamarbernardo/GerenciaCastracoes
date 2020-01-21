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
    private static int codigoMutirao;
    private Mutirao mutirao;

    private TextView dataMutirao;
    private TextView codigo;
    private TextView quantidadeAnimais;
    private TextView quantidadeListaEspera;
    private TextView quantidadeRoupinhas;
    private ImageView imagem;
    private ExpandableListView expandableListView;


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

            buildList();
            expandableListView = (ExpandableListView) findViewById(R.id.expandableListView);

            preencherDadosExpandableListView();


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

    public void preencherDadosExpandableListView() {
        expandableListView.setAdapter(new ExpandableAdapter(VisualizarMutirao.this, listGroup, listData));

        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                Toast.makeText(VisualizarMutirao.this, "Group: " + groupPosition + "| Item: " + childPosition, Toast.LENGTH_SHORT).show();
                return false;
            }
        });



        expandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {
                Toast.makeText(VisualizarMutirao.this, "Group (Expand): " + groupPosition, Toast.LENGTH_SHORT).show();
            }
        });

        expandableListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {
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

    public void buildList() {
        listGroup = new ArrayList<Cliente>();
        listData = new HashMap<Cliente, List<Animal>>();

        // GROUP
        listGroup = mutirao.getClientes();
        //listGroup.add(new Cliente(0, "Everlandsonsclesio", "(87) 98888-8888", "Em espécie"));
        //listGroup.add(new Cliente(1, "Francisco silva", "(87) 99641-1015", "Caixa"));
        //listGroup.add(new Cliente(2, "Gislaine Mota", "(87) 9911-1111", "BB"));
        //listGroup.add(new Cliente(3, "Abruplos Silva", "(87) 9222-2222", "Neon"));

        // CHILDREN
        List<Animal> auxList;
        int cont = 0;
        for (Cliente cliente : listGroup) {
            auxList = cliente.getAnimais();
            listData.put(listGroup.get(cont), auxList);
            cont++;
        }

        /**
         List<Animal> auxList = new ArrayList<Animal>();
         auxList.add(new Animal(0, "Garfild", "Gato", 'M', "SRD", "Amarelo com rosa"));
         auxList.add(new Animal(1, "Alex o leao", "Gato", 'M', "SRD", "Amarelo queimado"));
         auxList.add(new Animal(2, "Maious", "Cachorro", 'M',"Poodle", "Preto"));
         auxList.add(new Animal(3, "Lionel", "Gato", 'M',"SRD", "Cinza com branco"));
         listData.put(listGroup.get(0), auxList);

         auxList = new ArrayList<Animal>();
         auxList.add(new Animal(0, "Alicia", "Gato", 'F', "SRD", "Azul com rosa"));
         auxList.add(new Animal(1, "Maria Gasolina", "Gato", 'F', "SRD", "Amarelo queimado"));
         auxList.add(new Animal(2, "Mariola", "Cachorro", 'F',"Poodle", "Preto"));
         auxList.add(new Animal(3, "Lala", "Gato", 'F',"SRD", "Cinza com branco"));
         listData.put(listGroup.get(1), auxList);

         auxList = new ArrayList<Animal>();
         auxList.add(new Animal(0, "Gigi", "Cachorro", 'F', "Shitsu", "Amarelo com rosa"));
         auxList.add(new Animal(1, "Felicia", "Gato", 'F', "SRD", "Amarelo queimado"));
         auxList.add(new Animal(2, "Max", "Cachorro", 'M',"Poodle", "Preto"));
         auxList.add(new Animal(3, "Lili", "Gato", 'F',"SRD", "Cinza com branco"));
         listData.put(listGroup.get(2), auxList);

         auxList = new ArrayList<Animal>();
         auxList.add(new Animal(0, "Gaga", "Gato", 'M', "SRD", "Amarelo com rosa"));
         auxList.add(new Animal(1, "Liru", "Gato", 'F', "SRD", "Amarelo queimado"));
         auxList.add(new Animal(2, "Mioso", "Cachorro", 'M',"Poodle", "Preto"));
         auxList.add(new Animal(3, "Louis", "Gato", 'M',"SRD", "Cinza com branco"));
         listData.put(listGroup.get(3), auxList);
         **/

    }

    public void irTelaEditarMutirao(View view){
        Intent intent = new Intent(getApplicationContext(), EditarMutirao.class);
        Bundle parametos = new Bundle();
        parametos.putInt("codigo_mutirao", codigoMutirao);

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
