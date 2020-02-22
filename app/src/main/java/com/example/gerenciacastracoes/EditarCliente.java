package com.example.gerenciacastracoes;

import android.content.Intent;
import android.os.Bundle;

import com.example.gerenciacastracoes.negocio.entidades.Cliente;
import com.example.gerenciacastracoes.negocio.entidades.Mutirao;
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

public class EditarCliente extends AppCompatActivity {

    private int codigoMutirao;
    private int codigoCliente;
    private Castracoes fachada = Castracoes.getFachada();
    private Toolbar toolbar;

    //Cliente
    private EditText edtTxtNomeCliente;
    private EditText edtTxtTelefone;
    private EditText edtTxtTipoPagamento;
    private RadioButton radioBtPagouSim;
    private RadioButton radioBtPagouNao;
    private boolean pagou = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_cliente);


        inicializaToolbar();

        Intent intent = getIntent();
        Bundle parametros = intent.getExtras();
        if (parametros != null) {
            codigoMutirao = parametros.getInt("codigo_mutirao");
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

    public void inicializarElementos(){
        edtTxtNomeCliente = (EditText) findViewById(R.id.edtTxtNomeCliente);
        edtTxtTelefone = (EditText) findViewById(R.id.edtTextTelefone);
        criarMascaraTelefone();
        edtTxtTipoPagamento = (EditText) findViewById(R.id.edtTextTipoPagamento);
        radioBtPagouNao = (RadioButton) findViewById(R.id.radioBtPagouNao);
        radioBtPagouSim = (RadioButton) findViewById(R.id.radioBtPagouSim);
    }

    public void criarMascaraTelefone(){
        SimpleMaskFormatter simpleMaskFormatter = new SimpleMaskFormatter("(NN) NNNNN-NNNN");
        MaskTextWatcher maskTextWatcher = new MaskTextWatcher(edtTxtTelefone, simpleMaskFormatter);
        edtTxtTelefone.addTextChangedListener(maskTextWatcher);
    }

    public void preencherElementos(){
        Cliente cliente = fachada.buscarMutirao(codigoMutirao).procurarCliente(codigoCliente);
        if(cliente != null){
            edtTxtNomeCliente.setText(cliente.getNome());
            edtTxtTelefone.setText(cliente.getTelefone());
            edtTxtTipoPagamento.setText(cliente.getTipoDePagamento());
            if(cliente.isPagou()){
               radioBtPagouSim.setChecked(true);
            }else{
                radioBtPagouNao.setChecked(true);
            }
        }else{
            Toast.makeText(getApplicationContext(), "Cliente não encontrado!", Toast.LENGTH_SHORT).show();

        }

    }

    public void alterarCliente(View view){
        if(fazerVerificacoesCliente()){
            try {
                fachada.alterarCliente(codigoMutirao, codigoCliente, edtTxtNomeCliente.getText().toString(), edtTxtTelefone.getText().toString(), edtTxtTipoPagamento.getText().toString(), pagou);
                ClasseUtilitaria.emitirAlerta(EditarCliente.this, "Cliente editado!");
                irTelaVisualizarCliente();

            }catch (Exception ex){
                Toast.makeText(getApplicationContext(), ex.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }

    }


    public void irTelaVisualizarCliente(){
        Intent intent = new Intent(getApplicationContext(), VisualizarCliente.class);
        Bundle parametos = new Bundle();
        parametos.putInt("codigo_mutirao", codigoMutirao);
        parametos.putInt("codigo_cliente", codigoCliente);

        intent.putExtras(parametos);

        startActivity(intent);

    }

    public boolean fazerVerificacoesCliente() {
        boolean verificaDadosCliente = false;

        if (edtTxtNomeCliente.getText().length() > 0) {
            if (edtTxtTelefone.getText().length() > 0) {
                if (edtTxtTipoPagamento.getText().length() > 0) {
                    if (radioBtPagouSim.isChecked()) {
                        pagou = true;
                    }
                    verificaDadosCliente = true;
                } else {
                    Toast.makeText(getApplicationContext(), "Digite o tipo de pagamento!", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(getApplicationContext(), "Digite o telefone do cliente!", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(getApplicationContext(), "Digite o nome do cliente!", Toast.LENGTH_SHORT).show();
        }
        return verificaDadosCliente;
    }
}
