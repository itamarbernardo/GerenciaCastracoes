package com.example.gerenciacastracoes;

import android.content.Intent;
import android.os.Bundle;

import com.example.gerenciacastracoes.negocio.entidades.Animal;
import com.example.gerenciacastracoes.negocio.entidades.Cliente;
import com.example.gerenciacastracoes.negocio.entidades.Mutirao;
import com.example.gerenciacastracoes.negocio.fachada.Castracoes;
import com.example.gerenciacastracoes.ui.main.AnimalAdapter;
import com.example.gerenciacastracoes.ui.main.MutiraoAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class VisualizarCliente extends AppCompatActivity {

    private Castracoes fachada = Castracoes.getFachada();
    private int codigoMutirao;
    private int codigoCliente;
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
        setContentView(R.layout.activity_visualizar_cliente);

        Intent intentRecebeParametos = getIntent();
        Bundle parametros = intentRecebeParametos.getExtras();

        if (parametros != null) {
            codigoMutirao = parametros.getInt("codigo_mutirao");
            codigoCliente = parametros.getInt("codigo_cliente");

            cliente = fachada.buscarMutirao(codigoMutirao).procurarCliente(codigoCliente);

            inicializarObjetos();
            preencherCamposCliente();
            configurarListView();

        }


    }

    public void inicializarObjetos(){
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        txtNome = findViewById(R.id.txtNome);
        txtTelefone = findViewById(R.id.txtTelefone);
        txtTipoPagamento = findViewById(R.id.txtTipoPagamento);
        txtPagou = findViewById(R.id.txtPagou);
        listViewAnimais = findViewById(R.id.listViewAnimais);

    }

    public void preencherCamposCliente(){
        txtNome.setText(cliente.getNome());
        txtTelefone.setText(cliente.getTelefone());
        txtTipoPagamento.setText(cliente.getTipoDePagamento());

        if(cliente.isPagou()){
            txtPagou.setText("Sim");
        }else{
            txtPagou.setText("Não");
        }

    }

    public void configurarListView(){
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

}
