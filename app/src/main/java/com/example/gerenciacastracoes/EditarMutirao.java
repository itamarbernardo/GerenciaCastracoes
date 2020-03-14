package com.example.gerenciacastracoes;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;

import com.example.gerenciacastracoes.negocio.entidades.Mutirao;
import com.example.gerenciacastracoes.negocio.fachada.Castracoes;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.time.LocalDate;
import java.util.Calendar;

public class EditarMutirao extends AppCompatActivity {

    private static final String TAG = "EditarMutirao";

    private Castracoes fachada = Castracoes.getFachada();
    private Toolbar toolbar;

    private Button mDisplayDate;
    private Spinner tipoMutirao;

    private DatePickerDialog.OnDateSetListener mDateSetListener;
    private int year, month, day;
    private int dia, mes, ano;

    private int codigoMutirao;
    private LocalDate dataMutirao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_mutirao);

        inicializaToolbar();

        Intent intentRecebeParametos = getIntent();
        Bundle parametros = intentRecebeParametos.getExtras();

        if (parametros != null) {
            codigoMutirao = parametros.getInt("codigo_mutirao");

            mDisplayDate = (Button) findViewById(R.id.textData);
            configurarParametrosData();

            tipoMutirao = (Spinner) findViewById(R.id.spinnerTipoMutirao);
            ArrayAdapter adapter = ArrayAdapter.createFromResource(this, R.array.tipos_mutirao, android.R.layout.simple_list_item_1);
            tipoMutirao.setAdapter(adapter);

            Mutirao mutirao = fachada.buscarMutirao(codigoMutirao);
            if(mutirao != null){
                String data = ClasseUtilitaria.converterDataParaString(mutirao.getData());
                dataMutirao = mutirao.getData();
                mDisplayDate.setText(data);
                tipoMutirao.setSelection(adapter.getPosition(mutirao.getTipo()));
            }
        }else{
            Toast.makeText(EditarMutirao.this, "Erro ao transferir o código do mutirão!", Toast.LENGTH_SHORT).show();
        }

    }

    public void inicializaToolbar(){
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    public void configurarParametrosData(){

        mDisplayDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                year = cal.get(Calendar.YEAR);
                month = cal.get(Calendar.MONTH);
                day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        EditarMutirao.this,
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
    }

    public void alterarMutirao(View view){

        if (!tipoMutirao.getSelectedItem().toString().equals("Selecione o tipo")) {
            if(mes != 0){
                dataMutirao = LocalDate.of(ano, mes, dia);
            }
            try {
                fachada.alterarMutirao(codigoMutirao, dataMutirao, tipoMutirao.getSelectedItem().toString());

                ClasseUtilitaria.emitirAlerta(EditarMutirao.this, "Mutirão editado!");
                //Thread.sleep(10000);
                irTelaVisualizarMutirao(view);

            } catch (Exception ex) {
                Toast.makeText(getApplicationContext(), ex.getMessage(), Toast.LENGTH_LONG).show();
                Log.d(TAG, ex.getMessage());

            }
        }else{
            Toast.makeText(getApplicationContext(), "Selecione um tipo!", Toast.LENGTH_SHORT).show();
        }

    }

    public void irTelaVisualizarMutirao(View view){
        Intent intent = new Intent(getApplicationContext(), VisualizarMutirao.class);
        //Bundle parametos = new Bundle();
        //parametos.putInt("codigo_mutirao", codigoMutirao);

        //intent.putExtras(parametos);

        startActivity(intent);
    }

}
