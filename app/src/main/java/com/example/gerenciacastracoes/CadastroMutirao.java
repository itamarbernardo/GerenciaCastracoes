package com.example.gerenciacastracoes;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.example.gerenciacastracoes.negocio.fachada.Castracoes;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.time.LocalDate;
import java.util.Calendar;

public class CadastroMutirao extends AppCompatActivity {
    private static final String TAG = "CadastroMutirao";

    private Castracoes fachada = Castracoes.getFachada();
    private TextView mDisplayDate;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    private int year, month, day;
    private int dia, mes, ano;

    private Spinner tipoMutirao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_mutirao);

        mDisplayDate = (TextView) findViewById(R.id.textData);

        mDisplayDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                year = cal.get(Calendar.YEAR);
                month = cal.get(Calendar.MONTH);
                day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        CadastroMutirao.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        mDateSetListener,
                        year, month, day);
                //dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                Log.d(TAG, "onDateSet: dd/mm/yyyy: " + day + "/" + month + "/" + year);

                String date = day + "/" + month + "/" + year;
                dia = day;
                mes = month;
                ano = year;

                mDisplayDate.setText(date);
            }
        };

        tipoMutirao = (Spinner) findViewById(R.id.spinnerTipoMutirao);
        ArrayAdapter adapter = ArrayAdapter.createFromResource(this, R.array.tipos_mutirao, android.R.layout.simple_list_item_1);
        tipoMutirao.setAdapter(adapter);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


    }

    public void irTelaListagemMutiroes(View v){
        Intent telaListagemMutirao = new Intent(getApplicationContext(), ListagemMutiroes.class);
        startActivity(telaListagemMutirao);
        finish();
    }

    public void cadastrarMutirao(View view) {
        int codigo;
        if (!tipoMutirao.getSelectedItem().toString().equals("-")) {
            try {
                Log.d(TAG, "Cadastrar Mutirao: dd/mm/yyyy: " + dia + "/" + mes + "/" + ano);
                codigo = fachada.adicionarMutirao(LocalDate.of(ano, mes, dia), tipoMutirao.getSelectedItem().toString());

                ClasseUtilitaria.emitirAlerta(CadastroMutirao.this, "Mutirão cadastrado com sucesso!" + fachada.buscarMutirao(codigo));
                Thread.sleep(10000);
                irTelaListagemMutiroes(view);

            } catch (Exception ex) {
                Toast.makeText(getApplicationContext(), ex.getMessage(), Toast.LENGTH_SHORT).show();
                Log.d(TAG, ex.getMessage());

            }
        }else{
            Toast.makeText(getApplicationContext(), "Selecione um tipo!", Toast.LENGTH_SHORT).show();
        }
    }


}
