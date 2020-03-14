package com.example.gerenciacastracoes;

import android.content.Intent;
import android.os.Bundle;

import com.example.gerenciacastracoes.negocio.entidades.Animal;
import com.example.gerenciacastracoes.negocio.fachada.Castracoes;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

public class EditarAnimalListaEspera extends AppCompatActivity {

    private Castracoes fachada = Castracoes.getFachada();
    private int codigoMutirao;
    private int codigoCliente;
    private int codigoAnimal;

    private ArrayAdapter adapter;
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_animal_lista_espera);

        inicializaToolbar();

        Intent intent = getIntent();
        Bundle parametros = intent.getExtras();
        if (parametros != null) {
            codigoMutirao = parametros.getInt("codigo_mutirao");
            codigoCliente = parametros.getInt("codigo_cliente");
            codigoAnimal = parametros.getInt("codigo_animal");

            inicializaObjetos();
            preencherElementos();

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
        adapter = ArrayAdapter.createFromResource(this, R.array.tipos_animais, android.R.layout.simple_list_item_1);
        spinnerTipoAnimal.setAdapter(adapter);

        radioBtSexoM = (RadioButton) findViewById(R.id.radioBtSexoM);
        radioBtSexoF = (RadioButton) findViewById(R.id.radioBtSexoF);
        edtTxtPelagem = (EditText) findViewById(R.id.edtTxtPelagem);
        radioBtRoupinhaNao = (RadioButton) findViewById(R.id.radioBtRoupinhaNao);
        radioBtRoupinhaNao.setChecked(true);
        radioBtRoupinhaSim = (RadioButton) findViewById(R.id.radioBtRoupinhaSim);

    }

    public void preencherElementos() {
        Animal animal = fachada.procurarAnimal(codigoMutirao, codigoCliente, codigoAnimal);
        if (animal != null) {
            edtTxtNomeAnimal.setText(animal.getNome());
            edtTxtRaca.setText(animal.getRaca());
            spinnerTipoAnimal.setSelection(adapter.getPosition(animal.getTipo()));
            edtTxtPelagem.setText(animal.getPelagem());

            if (animal.getSexo() == 'M') {
                radioBtSexoM.setChecked(true);
            } else {
                radioBtSexoF.setChecked(true);
            }

            if (animal.isQuerRoupinha()) {
                radioBtRoupinhaSim.setChecked(true);
            } else {
                radioBtRoupinhaNao.setChecked(true);
            }

        } else {
            Toast.makeText(getApplicationContext(), "Animal não encontrado!", Toast.LENGTH_SHORT).show();

        }
    }

    public void alterarAnimal(View view) {
        if (fazerVerificacoesDadosAnimal()) {
            try {
                fachada.alterarAnimal(codigoMutirao, codigoCliente, codigoAnimal, edtTxtNomeAnimal.getText().toString(),
                        spinnerTipoAnimal.getSelectedItem().toString(), sexo, edtTxtRaca.getText().toString(), edtTxtPelagem.getText().toString(), querRoupinha);
                ClasseUtilitaria.emitirAlerta(EditarAnimalListaEspera.this, "Animal editado!");
                irTelaVisualizarAnimal();

            } catch (Exception ex) {
                Toast.makeText(getApplicationContext(), ex.getMessage(), Toast.LENGTH_SHORT).show();
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

    public void irTelaVisualizarAnimal() {
        Intent intent = new Intent(getApplicationContext(), VisualizarAnimalListaEspera.class);
        Bundle parametos = new Bundle();
        parametos.putInt("codigo_mutirao", codigoMutirao);
        parametos.putInt("codigo_cliente", codigoCliente);
        parametos.putInt("codigo_animal", codigoAnimal);
        intent.putExtras(parametos);

        startActivity(intent);
    }


}
