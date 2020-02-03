package com.example.gerenciacastracoes;

import android.content.Intent;
import android.os.Bundle;

import com.example.gerenciacastracoes.negocio.entidades.Animal;
import com.example.gerenciacastracoes.negocio.fachada.Castracoes;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class VisualizarAnimal extends AppCompatActivity {

    private Castracoes fachada = Castracoes.getFachada();
    private static int codigoMutirao;
    private static int codigoCliente;
    private static int codigoAnimal;

    private Toolbar toolbar;
    private TextView txtNome;
    private TextView txtTipo;
    private TextView txtSexo;
    private TextView txtRaca;
    private TextView txtPelagem;
    private TextView txtQuerRoupinha;
    private Animal animal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visualizar_animal);

        inicializaToolbar();

        Intent intentRecebeParametos = getIntent();
        Bundle parametros = intentRecebeParametos.getExtras();

        if (parametros != null) {
            codigoMutirao = parametros.getInt("codigo_mutirao");
            codigoCliente = parametros.getInt("codigo_cliente");
            codigoAnimal = parametros.getInt("codigo_animal");
        }
        animal = fachada.buscarMutirao(codigoMutirao).procurarCliente(codigoCliente).procurarAnimal(codigoAnimal);
        if (animal != null) {
            inicializaObjetos();
            preencherCamposAnimal();

        } else {
            Toast.makeText(getApplicationContext(), "Animal não encontrado!", Toast.LENGTH_SHORT).show();
        }


    }

    public void inicializaToolbar(){
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public void inicializaObjetos(){
        txtNome = (TextView) findViewById(R.id.txtNome);
        txtTipo = (TextView) findViewById(R.id.txtTipo);
        txtRaca = (TextView) findViewById(R.id.txtRaca);
        txtPelagem = (TextView) findViewById(R.id.txtPelagem);
        txtQuerRoupinha = (TextView) findViewById(R.id.txtQuerRoupinha);
        txtSexo = (TextView) findViewById(R.id.txtSexo);
    }

    public void preencherCamposAnimal(){
            txtNome.setText(animal.getNome());
            txtTipo.setText(animal.getTipo());
            txtRaca.setText(animal.getRaca());
            txtPelagem.setText(animal.getPelagem());
            txtSexo.setText(animal.getSexo()+ "");

            if(animal.isQuerRoupinha()){
                txtQuerRoupinha.setText("Sim");
            }else{
                txtQuerRoupinha.setText("Não");
            }

    }

}
