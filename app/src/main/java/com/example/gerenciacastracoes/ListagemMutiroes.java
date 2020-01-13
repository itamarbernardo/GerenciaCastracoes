package com.example.gerenciacastracoes;

import android.content.Intent;
import android.os.Bundle;

import com.example.gerenciacastracoes.negocio.entidades.Mutirao;
import com.example.gerenciacastracoes.negocio.fachada.Castracoes;
import com.example.gerenciacastracoes.ui.main.MutiraoAdapter;

import androidx.appcompat.app.AppCompatActivity;

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

        if(mutiroes != null){
            ArrayAdapter adapter = new MutiraoAdapter(this, mutiroes);
            listaMutiroes.setAdapter(adapter);
        }


    }


    public void irTelaCadastro(View view) {
        Intent intent1 = new Intent(getApplicationContext(), CadastroMutiraoActivity.class);

        startActivity(intent1);
    }
}
