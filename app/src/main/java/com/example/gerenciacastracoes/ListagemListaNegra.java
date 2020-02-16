package com.example.gerenciacastracoes;

import android.content.Intent;
import android.os.Bundle;

import com.example.gerenciacastracoes.negocio.entidades.Cliente;
import com.example.gerenciacastracoes.negocio.entidades.Mutirao;
import com.example.gerenciacastracoes.negocio.fachada.Castracoes;
import com.example.gerenciacastracoes.ui.main.ClienteListaNegraAdapter;
import com.example.gerenciacastracoes.ui.main.MutiraoAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class ListagemListaNegra extends AppCompatActivity {

    private static final String TAG = "ListagemListaNegra";

    private Castracoes fachada = Castracoes.getFachada();
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listagem_lista_negra);

        inicializaToolbar();

        ListView listaNegraClientes = (ListView) findViewById(R.id.listViewClientesListaNegra);
        final ArrayList<Cliente> clientes = (ArrayList<Cliente>) fachada.listagemClientesListaNegra();

        //Aqui dá pra colocar uma imagem de fundo se não houver mutiroes para mostrar e quando houver
        //só alterar a visibilidade da imagem
        if(clientes != null){
            ArrayAdapter adapter = new ClienteListaNegraAdapter(this, clientes);
            listaNegraClientes.setAdapter(adapter);

            listaNegraClientes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    //Toast.makeText(getApplicationContext(), "Código: " + clientes.get(position).getCodigo(), Toast.LENGTH_SHORT).show();
                    irTelaVisualizarClienteListaNegra(view, clientes.get(position).getCodigo());
                }
            });
        }


    }

    public void inicializaToolbar(){
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    public void irTelaVisualizarClienteListaNegra(View v, int codigo){
        Intent intent = new Intent(getApplicationContext(), VisualizarListaNegra.class);
        Bundle parametos = new Bundle();
        parametos.putInt("codigo_cliente", codigo);

        intent.putExtras(parametos);

        startActivity(intent);
        //finish();
    }

    public void irTelaCadastro(View view) {
        Intent intent1 = new Intent(getApplicationContext(), CadastroListaNegra.class);
        startActivity(intent1);
        //finish();
    }


}
