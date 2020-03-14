package com.example.gerenciacastracoes;

import android.content.Intent;
import android.os.Bundle;

import com.example.gerenciacastracoes.negocio.fachada.Castracoes;
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

public class CadastrarAnimal extends AppCompatActivity {

    private Castracoes fachada = Castracoes.getFachada();
    private int codigoMutirao;
    private int codigoCliente;

    private Toolbar toolbar;
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

    private static final String TAG = "CadastrarAnimal";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrar_animal);

        inicializaToolbar();

        Intent intentRecebeParametos = getIntent();
        Bundle parametros = intentRecebeParametos.getExtras();

        if (parametros != null) {
            codigoMutirao = parametros.getInt("codigo_mutirao");
            codigoCliente = parametros.getInt("codigo_cliente");

            inicializaObjetos();

        } else {
            Toast.makeText(getApplicationContext(), "Erro ao transferir os dados!", Toast.LENGTH_SHORT).show();
        }

    }

    public void inicializaToolbar() {
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    public void inicializaObjetos() {
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

    public void cadastrarAnimal(View view) {
        if (fazerVerificacoesDadosAnimal()) {
            try {

                fachada.adicionarAnimal(codigoMutirao, codigoCliente, edtTxtNomeAnimal.getText().toString(), spinnerTipoAnimal.getSelectedItem().toString(), sexo, edtTxtRaca.getText().toString(), edtTxtPelagem.getText().toString(), querRoupinha);
                ClasseUtilitaria.emitirAlerta(CadastrarAnimal.this, "Animal cadastrado com sucesso!");
                irTelaVisualizarCliente();

            } catch (Exception ex) {
                Toast.makeText(getApplicationContext(), ex.getMessage(), Toast.LENGTH_SHORT).show();
                Log.d(TAG, ex.getMessage());

            }
        }

    }

    public boolean fazerVerificacoesDadosAnimal() {
        boolean verificaDadosAnimal = false;

        if (edtTxtNomeAnimal.getText().length() > 0) {
            if (edtTxtRaca.getText().length() > 0) {
                if (!spinnerTipoAnimal.getSelectedItem().toString().equals("Selecione o tipo")) {
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

    public void irTelaVisualizarCliente() {
        Intent intent = new Intent(getApplicationContext(), VisualizarCliente.class);
        Bundle parametos = new Bundle();
        parametos.putInt("codigo_mutirao", codigoMutirao);
        parametos.putInt("codigo_cliente", codigoCliente);

        intent.putExtras(parametos);

        startActivity(intent);
    }


}
