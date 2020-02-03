package com.example.gerenciacastracoes;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.example.gerenciacastracoes.negocio.entidades.Animal;
import com.example.gerenciacastracoes.negocio.entidades.Cliente;
import com.example.gerenciacastracoes.negocio.fachada.Castracoes;
import com.example.gerenciacastracoes.ui.main.AnimalAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class VisualizarClienteListaEspera extends AppCompatActivity {

    private Castracoes fachada = Castracoes.getFachada();
    private static int codigoMutirao;
    private static int codigoCliente;
    private ArrayList<Animal> animais;
    private Cliente cliente;

    private Toolbar toolbar;
    private TextView txtNome;
    private TextView txtTelefone;
    private TextView txtTipoPagamento;
    private TextView txtPagou;
    private ListView listViewAnimais;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visualizar_cliente_lista_espera);

        inicializaToolbar();

        Intent intentRecebeParametos = getIntent();
        Bundle parametros = intentRecebeParametos.getExtras();

        if (parametros != null) {
            codigoMutirao = parametros.getInt("codigo_mutirao");
            codigoCliente = parametros.getInt("codigo_cliente");
        }

        cliente = fachada.buscarMutirao(codigoMutirao).procurarClienteListaEspera(codigoCliente);
        if (cliente != null) {
            inicializarObjetos();
            preencherCamposCliente();
            configurarListView();
        } else {
            Toast.makeText(getApplicationContext(), "Cliente não encontrado!", Toast.LENGTH_SHORT).show();
        }


    }

    public void inicializarObjetos() {
        txtNome = findViewById(R.id.txtNome);
        txtTelefone = findViewById(R.id.txtTelefone);
        txtTipoPagamento = findViewById(R.id.txtTipoPagamento);
        txtPagou = findViewById(R.id.txtPagou);
        listViewAnimais = findViewById(R.id.listViewAnimais);

    }

    public void inicializaToolbar(){
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    public void preencherCamposCliente() {
        txtNome.setText(cliente.getNome());
        txtTelefone.setText(cliente.getTelefone());
        txtTipoPagamento.setText(cliente.getTipoDePagamento());

        if (cliente.isPagou()) {
            txtPagou.setText("Sim");
        } else {
            txtPagou.setText("Não");
        }

    }

    public void configurarListView() {
        animais = (ArrayList<Animal>) cliente.getAnimais();

        if (animais != null) {
            ArrayAdapter adapter = new AnimalAdapter(this, animais);
            listViewAnimais.setAdapter(adapter);

            listViewAnimais.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Toast.makeText(getApplicationContext(), "Código: " + animais.get(position).toString(), Toast.LENGTH_SHORT).show();
                    //irTelaVisualizarAnimal(view, mutiroes.get(position).getCodigo());
                }
            });
        }
    }


    public void removerClienteListaEspera(View view) {
        AlertDialog.Builder alerta = new AlertDialog.Builder(this);
        alerta.setTitle("Remover...");
        alerta.setCancelable(false); //Se tiver true, permite que a caixa de dialogo suma se clicar fora da caixa de texto.
        alerta.setIcon(R.mipmap.ic_delete);
        alerta.setMessage("Tem certeza que deseja excluir este cliente da lista de espera?");
        alerta.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                try {
                    fachada.removerClienteListaEspera(codigoMutirao, codigoCliente);
                    ClasseUtilitaria.emitirAlerta(VisualizarClienteListaEspera.this, "Cliente excluído da lista de espera com sucesso!");
                    irTelaVisualizarMutirao();

                } catch (Exception e) {
                    Toast.makeText(VisualizarClienteListaEspera.this, e.getMessage(), Toast.LENGTH_SHORT).show();
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

    public void irTelaVisualizarMutirao() {
        Intent intent = new Intent(getApplicationContext(), VisualizarMutirao.class);
        startActivity(intent);
    }

    public void irTelaEditarClienteListaEspera(View view) {
        Intent intent = new Intent(getApplicationContext(), EditarClienteListaEspera.class);
        Bundle parametos = new Bundle();
        parametos.putInt("codigo_mutirao", codigoMutirao);
        parametos.putInt("codigo_cliente", codigoCliente);

        intent.putExtras(parametos);

        startActivity(intent);


    }

    public void transferirCliente(View view){
        AlertDialog.Builder alerta = new AlertDialog.Builder(this);
        alerta.setTitle("Transferir...");
        alerta.setCancelable(false); //Se tiver true, permite que a caixa de dialogo suma se clicar fora da caixa de texto.
        alerta.setIcon(R.mipmap.ic_swap);
        alerta.setMessage("Tem certeza que deseja transferir este cliente?");
        alerta.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                try {
                    fachada.transferirCliente(codigoMutirao, codigoCliente);
                    ClasseUtilitaria.emitirAlerta(VisualizarClienteListaEspera.this, "Cliente transferido com sucesso!");
                    irTelaVisualizarMutirao();

                } catch (Exception e) {
                    Toast.makeText(VisualizarClienteListaEspera.this, e.getMessage(), Toast.LENGTH_SHORT).show();
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


}
