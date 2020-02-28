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

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class ListagemListaNegra extends AppCompatActivity {

    private static final String TAG = "ListagemListaNegra";

    private Castracoes fachada = Castracoes.getFachada();
    private Toolbar toolbar;
    private ListView listaNegraClientes;
    private ArrayList<Cliente> clientes;
    private EditText edtTxtPesquisa;
    private TextView txtTitulo;
    private ClienteListaNegraAdapter adapter;
    private boolean verificaCliqueBotao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listagem_lista_negra);

        inicializaToolbar();
        inicializaElementos();

        if(clientes != null){
            adapter = new ClienteListaNegraAdapter(this, clientes);
            listaNegraClientes.setAdapter(adapter);

            listaNegraClientes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    //Toast.makeText(getApplicationContext(), "Código: " + clientes.get(position).getCodigo(), Toast.LENGTH_SHORT).show();
                    irTelaVisualizarClienteListaNegra(view, clientes.get(position).getCodigo());
                }
            });

            //Aplica o filtro
            edtTxtPesquisa.addTextChangedListener(new TextWatcher() {
                @Override
                public void onTextChanged(CharSequence s, int start, int before,
                                          int count) {
                    //quando o texto é alterado chamamos o filtro.
                    adapter.getFilter().filter(s.toString());
                }

                @Override
                public void beforeTextChanged(CharSequence s, int start, int count,
                                              int after) {
                }

                @Override
                public void afterTextChanged(Editable s) {
                }
            });


        }



    }

    public void inicializaElementos(){
        listaNegraClientes = (ListView) findViewById(R.id.listViewClientesListaNegra);
        clientes = (ArrayList<Cliente>) fachada.listagemClientesListaNegra();
        edtTxtPesquisa = (EditText) findViewById(R.id.edtTxtPesquisa);
        txtTitulo = (TextView) findViewById(R.id.txtTitulo);
        verificaCliqueBotao = false;
    }

    public void inicializaToolbar(){
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

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
