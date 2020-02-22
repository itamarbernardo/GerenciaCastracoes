package com.example.gerenciacastracoes;

import android.content.Intent;
import android.os.Bundle;

import com.example.gerenciacastracoes.negocio.entidades.Mutirao;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import java.time.LocalDate;

public class CadastrarCliente extends AppCompatActivity {

    private int codigoMutirao;
    private Castracoes fachada = Castracoes.getFachada();

    private Toolbar toolbar;

    //Cliente
    private EditText edtTxtNomeCliente;
    private EditText edtTxtTelefone;
    private EditText edtTxtTipoPagamento;
    private RadioButton radioBtPagouSim;
    private RadioButton radioBtPagouNao;
    private boolean pagou = false;

    //Animal
    private EditText edtTxtNomeAnimal;
    private EditText edtTxtRaca;
    private Spinner spinnerTipoAnimal;
    private RadioButton radioBtSexoM;
    private RadioButton radioBtSexoF;
    private char sexo;
    private EditText edtTxtPelagem;
    private RadioButton radioBtRoupinhaSim;
    private RadioButton radioBtRoupinhaNao;
    private boolean querRoupinha = false;

    private static final String TAG = "CadastrarCliente";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrar_cliente);

        inicializaToolbar();

        Intent intent = getIntent();
        Bundle parametros = intent.getExtras();
        if (parametros != null) {
            codigoMutirao = parametros.getInt("codigo_mutirao");

            inicializaObjetos();


        } else {
            Toast.makeText(CadastrarCliente.this, "Erro ao passar o código do mutirão!", Toast.LENGTH_SHORT).show();
        }
    }

    public void criarMascaraTelefone(){
        SimpleMaskFormatter simpleMaskFormatter = new SimpleMaskFormatter("(NN) NNNNN-NNNN");
        MaskTextWatcher maskTextWatcher = new MaskTextWatcher(edtTxtTelefone, simpleMaskFormatter);
        edtTxtTelefone.addTextChangedListener(maskTextWatcher);
    }

    public void inicializaObjetos(){
        edtTxtNomeCliente = (EditText) findViewById(R.id.edtTxtNomeCliente);
        edtTxtTelefone = (EditText) findViewById(R.id.edtTextTelefone);
        criarMascaraTelefone();
        edtTxtTipoPagamento = (EditText) findViewById(R.id.edtTextTipoPagamento);
        radioBtPagouNao = (RadioButton) findViewById(R.id.radioBtPagouNao);
        radioBtPagouNao.setChecked(true);
        radioBtPagouSim = (RadioButton) findViewById(R.id.radioBtPagouSim);


        edtTxtNomeAnimal = (EditText) findViewById(R.id.edtTextNomeAnimal);
        edtTxtRaca = (EditText) findViewById(R.id.edtTxtRaca);
        spinnerTipoAnimal = (Spinner) findViewById(R.id.spinnerTipoAnimal);
        ArrayAdapter adapter = ArrayAdapter.createFromResource(this, R.array.tipos_animais, android.R.layout.simple_list_item_1);
        spinnerTipoAnimal.setAdapter(adapter);
        radioBtSexoM = (RadioButton) findViewById(R.id.radioBtSexoM);
        radioBtSexoF = (RadioButton) findViewById(R.id.radioBtSexoF);
        edtTxtPelagem = (EditText) findViewById(R.id.edtTxtPelagem);
        radioBtRoupinhaNao = (RadioButton) findViewById(R.id.radioBtRoupinhaNao);
        radioBtRoupinhaNao.setChecked(true);
        radioBtRoupinhaSim = (RadioButton) findViewById(R.id.radioBtRoupinhaSim);

    }

    public void inicializaToolbar() {
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public void irTelaVisualizarMutirao(View v){
        Intent intent = new Intent(getApplicationContext(), VisualizarMutirao.class);
        startActivity(intent);
        //finish();
    }

    public void cadastrarCliente(View view) {
        if (fazerVerificacoesCliente() && fazerVerificacoesDadosAnimal()) {
            try {

                fachada.adicionarCliente(codigoMutirao, edtTxtNomeCliente.getText().toString(), edtTxtTelefone.getText().toString(), edtTxtTipoPagamento.getText().toString(),
                        pagou, edtTxtNomeAnimal.getText().toString(), spinnerTipoAnimal.getSelectedItem().toString(), sexo, edtTxtRaca.getText().toString(), edtTxtPelagem.getText().toString(), querRoupinha);

                ClasseUtilitaria.emitirAlerta(CadastrarCliente.this, "Cliente cadastrado com sucesso!");
                //Thread.sleep(10000);
                irTelaVisualizarMutirao(view);

            } catch (Exception ex) {
                Toast.makeText(getApplicationContext(), ex.getMessage(), Toast.LENGTH_SHORT).show();
                Log.d(TAG, ex.getMessage());

            }
        }
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

    public boolean fazerVerificacoesDadosAnimal() {
        boolean verificaDadosAnimal = false;

        if (edtTxtNomeAnimal.getText().length() > 0) {
            if (edtTxtRaca.getText().length() > 0) {
                if (!spinnerTipoAnimal.getSelectedItem().toString().equals("-")) {
                    preencherSexoAnimal();
                    if (sexo != 'E') {
                        if (edtTxtPelagem.getText().length() > 0) {
                            if (radioBtRoupinhaSim.isChecked()) {
                                querRoupinha = true;
                            }
                            verificaDadosAnimal = true;
                        } else {
                            Toast.makeText(getApplicationContext(), "Digite a pelagem do animal!", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "Selecione o sexo do animal!", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Selecione o tipo de animal!", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(getApplicationContext(), "Digite a raça do animal!", Toast.LENGTH_SHORT).show();
            }

        } else {
            Toast.makeText(getApplicationContext(), "Digite o nome do animal!", Toast.LENGTH_SHORT).show();
        }
        return verificaDadosAnimal;
    }

    public void preencherSexoAnimal() {
        if (radioBtSexoM.isChecked()) {
            sexo = 'M';
        } else if (radioBtSexoF.isChecked()) {
            sexo = 'F';
        } else {
            sexo = 'E';
        }
    }


}
