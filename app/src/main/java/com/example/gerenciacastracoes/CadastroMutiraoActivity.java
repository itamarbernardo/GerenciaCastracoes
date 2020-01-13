package com.example.gerenciacastracoes;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import com.example.gerenciacastracoes.negocio.fachada.Castracoes;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gerenciacastracoes.R;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;

public class CadastroMutiraoActivity extends AppCompatActivity {
    private static final String TAG = "CadastroMutiraoActivity";

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
                        CadastroMutiraoActivity.this,
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


    }

    public void cadastrarMutirao(View view) {
        int codigo;
        if (!tipoMutirao.getSelectedItem().toString().equals("-")) {
            try {
                Log.d(TAG, "Cadastrar Mutirao: dd/mm/yyyy: " + dia + "/" + mes + "/" + ano);
                codigo = fachada.adicionarMutirao(LocalDate.of(ano, mes, dia), tipoMutirao.getSelectedItem().toString());

                emitirAlerta("Mutirão cadastrado com sucesso!" + fachada.buscarMutirao(codigo));

            } catch (Exception ex) {
                Toast.makeText(getApplicationContext(), ex.getMessage(), Toast.LENGTH_SHORT).show();
                Log.d(TAG, ex.getMessage());

            }
        }else{
            Toast.makeText(getApplicationContext(), "Selecione um tipo!", Toast.LENGTH_SHORT).show();
        }
    }

    public void emitirAlerta(String mensagem) {
        AlertDialog.Builder alerta = new AlertDialog.Builder(CadastroMutiraoActivity.this);
        alerta.setTitle("Aviso");
        alerta.setCancelable(true); //Se tiver true, permite que a caixa de dialogo suma se clicar fora da caixa de texto.
        alerta.setIcon(R.mipmap.ic_interrogacao);
        alerta.setMessage(mensagem);
        alerta.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        //Pra colocar outra opção de Cancelar, por ex, só é fazer alerta.setNegativeButton
        AlertDialog alertDialog = alerta.create();
        alertDialog.show();
    }
}
