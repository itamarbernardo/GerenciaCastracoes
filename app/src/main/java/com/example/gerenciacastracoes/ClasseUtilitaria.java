package com.example.gerenciacastracoes;

import android.content.Context;
import android.content.DialogInterface;

import androidx.appcompat.app.AlertDialog;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class ClasseUtilitaria {

    public static void emitirAlerta(Context context, String mensagem) {
        AlertDialog.Builder alerta = new AlertDialog.Builder(context);
        alerta.setTitle("Aviso");
        alerta.setCancelable(false); //Se tiver true, permite que a caixa de dialogo suma se clicar fora da caixa de texto.
        alerta.setIcon(R.mipmap.ic_interrogacao);
        alerta.setMessage(mensagem);
        alerta.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        //Pra colocar outra opção de Cancelar, por ex, só é fazer alerta.setNegativeButton
        //AlertDialog alertDialog = alerta.create();
        //alertDialog.show();
        alerta.show();
    }

    public static String converterDataParaString(LocalDate data) {
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        return data.format(formato); //Deixa no formato conhecido.
    }

    public static String converterDataParaStringFormatoTracinho(LocalDate data) {
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd-MM-yyyy");

        return data.format(formato); //Deixa no formato conhecido.
    }

    public static int dp2px(Context ctx, float dp) {
        final float scale = ctx.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }

}
