package com.example.gerenciacastracoes;

import android.content.Intent;
import android.os.Bundle;

import com.example.gerenciacastracoes.negocio.entidades.Mutirao;
import com.example.gerenciacastracoes.negocio.fachada.Castracoes;
import com.example.gerenciacastracoes.ui.main.MutiraoAdapter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class ListagemMutiroes extends AppCompatActivity {

    private static final String TAG = "ListagemMutiroes";

    private Castracoes fachada = Castracoes.getFachada();
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listagem_mutiroes);

        ListView listaMutiroes = (ListView) findViewById(R.id.listViewMutiroes);
        final ArrayList<Mutirao> mutiroes = (ArrayList<Mutirao>) fachada.listagemMutiroes();

        //Aqui dá pra colocar uma imagem de fundo se não houver mutiroes para mostrar e quando houver
        //só alterar a visibilidade da imagem
        if(mutiroes != null){
            ArrayAdapter adapter = new MutiraoAdapter(this, mutiroes);
            listaMutiroes.setAdapter(adapter);

            listaMutiroes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    //Toast.makeText(getApplicationContext(), "Código: " + mutiroes.get(position).getCodigo(), Toast.LENGTH_SHORT).show();
                    irTelaVisualizarMutirao(view, mutiroes.get(position).getCodigo());
                }
            });
        }



        inicializaToolbar();


    }

    public void inicializaToolbar(){
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    public void irTelaVisualizarMutirao(View v, int codigo){
        Intent intent = new Intent(getApplicationContext(), VisualizarMutirao.class);
        Bundle parametos = new Bundle();
        parametos.putInt("codigo_mutirao", codigo);

        intent.putExtras(parametos);

        startActivity(intent);
        //finish();
    }

    public void irTelaCadastro(View view) {
        Intent intent1 = new Intent(getApplicationContext(), CadastroMutirao.class);
        startActivity(intent1);
        //finish();
    }
}
