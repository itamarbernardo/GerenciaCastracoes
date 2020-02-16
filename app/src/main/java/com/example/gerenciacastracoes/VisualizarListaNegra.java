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

public class VisualizarListaNegra extends AppCompatActivity {

    private Castracoes fachada = Castracoes.getFachada();
    private static int codigoCliente;
    private Cliente cliente;

    private Toolbar toolbar;
    private TextView txtNome;
    private TextView txtTelefone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visualizar_lista_negra);

        inicializaToolbar();

        Intent intentRecebeParametos = getIntent();
        Bundle parametros = intentRecebeParametos.getExtras();

        if (parametros != null) {
            codigoCliente = parametros.getInt("codigo_cliente");
        }
        cliente = fachada.buscarClienteListaNegra(codigoCliente);
        if (cliente != null) {
            inicializarObjetos();
            preencherCamposCliente();
        } else {
            Toast.makeText(getApplicationContext(), "Cliente não encontrado!", Toast.LENGTH_SHORT).show();
        }


    }


    public void inicializaToolbar() {
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public void inicializarObjetos() {
        txtNome = findViewById(R.id.txtNome);
        txtTelefone = findViewById(R.id.txtTelefone);

    }

    public void preencherCamposCliente() {
        txtNome.setText(cliente.getNome());
        txtTelefone.setText(cliente.getTelefone());

    }


    public void irTelaEditarCliente(View view) {
        Intent intent = new Intent(getApplicationContext(), EditarListaNegra.class);
        Bundle parametos = new Bundle();
        parametos.putInt("codigo_cliente", codigoCliente);

        intent.putExtras(parametos);

        startActivity(intent);

    }

    public void removerCliente(View view) {
        AlertDialog.Builder alerta = new AlertDialog.Builder(this);
        alerta.setTitle("Remover...");
        alerta.setCancelable(false); //Se tiver true, permite que a caixa de dialogo suma se clicar fora da caixa de texto.
        alerta.setIcon(R.mipmap.ic_delete);
        alerta.setMessage("Tem certeza que deseja remover este cliente da LISTA NEGRA?");
        alerta.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                try {
                    fachada.removerClienteDaListaNegra(codigoCliente);
                    ClasseUtilitaria.emitirAlerta(VisualizarListaNegra.this, "Cliente excluído com sucesso!");
                    irTelaListagemListaNegra();

                } catch (Exception e) {
                    Toast.makeText(VisualizarListaNegra.this, e.getMessage(), Toast.LENGTH_SHORT).show();
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

    public void irTelaListagemListaNegra() {
        Intent intent = new Intent(getApplicationContext(), ListagemListaNegra.class);
        startActivity(intent);
    }



}
