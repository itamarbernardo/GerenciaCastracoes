package com.example.gerenciacastracoes;

import android.content.Intent;
import android.os.Bundle;

import com.example.gerenciacastracoes.negocio.fachada.Castracoes;
import com.github.rtoshiro.util.format.SimpleMaskFormatter;
import com.github.rtoshiro.util.format.text.MaskTextWatcher;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

public class CadastroListaNegra extends AppCompatActivity {

    private Castracoes fachada = Castracoes.getFachada();

    private Toolbar toolbar;

    //Cliente
    private EditText edtTxtNomeCliente;
    private EditText edtTxtTelefone;


    private static final String TAG = "CadastroListaNegra";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_lista_negra);

        inicializaToolbar();
        inicializaObjetos();

    }

    public void inicializaObjetos() {
        edtTxtNomeCliente = (EditText) findViewById(R.id.edtTxtNomeCliente);
        edtTxtTelefone = (EditText) findViewById(R.id.edtTextTelefone);
        criarMascaraTelefone();

    }

    public void criarMascaraTelefone() {
        SimpleMaskFormatter simpleMaskFormatter = new SimpleMaskFormatter("(NN) NNNNN-NNNN");
        MaskTextWatcher maskTextWatcher = new MaskTextWatcher(edtTxtTelefone, simpleMaskFormatter);
        edtTxtTelefone.addTextChangedListener(maskTextWatcher);
    }

    public void inicializaToolbar() {
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public void irTelaListagemClienteListaNegra(View v) {
        Intent intent = new Intent(getApplicationContext(), ListagemListaNegra.class);
        startActivity(intent);
        //finish();
    }

    public void cadastrarListaNegra(View view) {
        if (fazerVerificacoesCliente()) {
            try {

                fachada.adicionarClienteAListaNegra(edtTxtNomeCliente.getText().toString(), edtTxtTelefone.getText().toString(), "Vazio");

                ClasseUtilitaria.emitirAlerta(CadastroListaNegra.this, "Cliente inserido na Lista Negra com sucesso!");
                //Thread.sleep(10000);
                irTelaListagemClienteListaNegra(view);

            } catch (Exception ex) {
                Toast.makeText(getApplicationContext(), ex.getMessage(), Toast.LENGTH_SHORT).show();
                Log.d(TAG, ex.getMessage());
                ex.printStackTrace();

            }
        }
    }

    public boolean fazerVerificacoesCliente() {
        boolean verificaDadosCliente = false;

        if (edtTxtNomeCliente.getText().length() > 0) {
            if (edtTxtTelefone.getText().length() > 0) {
                verificaDadosCliente = true;

            } else {
                Toast.makeText(getApplicationContext(), "Digite o telefone do cliente!", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(getApplicationContext(), "Digite o nome do cliente!", Toast.LENGTH_SHORT).show();
        }
        return verificaDadosCliente;
    }


}
