package com.example.gerenciacastracoes;

import android.content.Intent;
import android.os.Bundle;

import com.example.gerenciacastracoes.negocio.entidades.Mutirao;
import com.example.gerenciacastracoes.negocio.fachada.Castracoes;
import com.example.gerenciacastracoes.ui.main.MutiraoAdapter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ListagemMutiroes extends AppCompatActivity {

    private static final String TAG = "ListagemMutiroes";

    private Castracoes fachada = Castracoes.getFachada();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listagem_mutiroes);

        ListView listaMutiroes = (ListView) findViewById(R.id.listViewMutiroes);
        ArrayList<Mutirao> mutiroes = (ArrayList<Mutirao>) fachada.listagemMutiroes();

        //Aqui dá pra colocar uma imagem de fundo se não houver mutiroes para mostrar e quando houver
        //só alterar a visibilidade da imagem
        if(mutiroes != null){
            ArrayAdapter adapter = new MutiraoAdapter(this, mutiroes);
            listaMutiroes.setAdapter(adapter);
        }


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


    }


    public void irTelaCadastro(View view) {
        Intent intent1 = new Intent(getApplicationContext(), CadastroMutiraoActivity.class);
        startActivity(intent1);
    }
}
