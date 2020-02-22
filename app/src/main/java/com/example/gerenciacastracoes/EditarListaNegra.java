package com.example.gerenciacastracoes;

import android.content.Intent;
import android.os.Bundle;

import com.example.gerenciacastracoes.negocio.entidades.Cliente;
import com.example.gerenciacastracoes.negocio.fachada.Castracoes;
import com.github.rtoshiro.util.format.SimpleMaskFormatter;
import com.github.rtoshiro.util.format.text.MaskTextWatcher;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

public class EditarListaNegra extends AppCompatActivity {

    private int codigoCliente;
    private Castracoes fachada = Castracoes.getFachada();
    private Toolbar toolbar;

    //Cliente
    private EditText edtTxtNomeCliente;
    private EditText edtTxtTelefone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_lista_negra);


        inicializaToolbar();

        Intent intent = getIntent();
        Bundle parametros = intent.getExtras();
        if (parametros != null) {
            codigoCliente = parametros.getInt("codigo_cliente");

            inicializarElementos();
            preencherElementos();

        }
        else{
            Toast.makeText(getApplicationContext(), "Erro ao transferir os dados!", Toast.LENGTH_SHORT).show();
        }
    }

    public void inicializaToolbar(){
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    public void criarMascaraTelefone(){
        SimpleMaskFormatter simpleMaskFormatter = new SimpleMaskFormatter("(NN) NNNNN-NNNN");
        MaskTextWatcher maskTextWatcher = new MaskTextWatcher(edtTxtTelefone, simpleMaskFormatter);
        edtTxtTelefone.addTextChangedListener(maskTextWatcher);
    }

    public void inicializarElementos(){
        edtTxtNomeCliente = (EditText) findViewById(R.id.edtTxtNomeCliente);
        edtTxtTelefone = (EditText) findViewById(R.id.edtTextTelefone);
        criarMascaraTelefone();
    }

    public void preencherElementos(){
        Cliente cliente = fachada.buscarClienteListaNegra(codigoCliente);
        if(cliente != null){

            edtTxtNomeCliente.setText(cliente.getNome());
            edtTxtTelefone.setText(cliente.getTelefone());

        }else{
            Toast.makeText(getApplicationContext(), "Cliente não encontrado!", Toast.LENGTH_SHORT).show();

        }

    }

    public void alterarCliente(View view){
        if(fazerVerificacoesCliente()){
            try {
                fachada.alterarClienteListaNegra(codigoCliente, edtTxtNomeCliente.getText().toString(), edtTxtTelefone.getText().toString(), "Vazio");
                ClasseUtilitaria.emitirAlerta(EditarListaNegra.this, "Cliente editado!");
                irTelaVisualizarCliente();

            }catch (Exception ex){
                Toast.makeText(getApplicationContext(), ex.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }

    }


    public void irTelaVisualizarCliente(){
        Intent intent = new Intent(getApplicationContext(), VisualizarListaNegra.class);
        Bundle parametos = new Bundle();
        parametos.putInt("codigo_cliente", codigoCliente);

        intent.putExtras(parametos);

        startActivity(intent);

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
