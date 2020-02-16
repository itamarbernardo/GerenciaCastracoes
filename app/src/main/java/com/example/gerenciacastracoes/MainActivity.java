package com.example.gerenciacastracoes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.Image;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.File;
import java.io.FileOutputStream;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "ListagemMutiroes";

    private FloatingActionButton btAdicionarMutirao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btAdicionarMutirao = (FloatingActionButton) findViewById(R.id.btAdicionarMutirao);

        btAdicionarMutirao.setOnClickListener(new Button.OnClickListener()
                                              {
                                                  public void onClick(View v){
                                                      AlertDialog.Builder alerta = new AlertDialog.Builder(MainActivity.this);
                                                      alerta.setTitle("Aviso");
                                                      alerta.setCancelable(true); //Se tiver true, permite que a caixa de dialogo suma se clicar fora da caixa de texto.
                                                      alerta.setIcon(R.mipmap.ic_interrogacao);
                                                      alerta.setMessage("Botão clicado!");
                                                      alerta.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                                          @Override
                                                          public void onClick(DialogInterface dialog, int which) {
                                                              Toast.makeText(getApplicationContext(), "Ok escolhido", Toast.LENGTH_SHORT).show();
                                                          }
                                                      });
                                                      //Pra colocar outra opção de Cancelar, por ex, só é fazer alerta.setNegativeButton



                                                      AlertDialog alertDialog = alerta.create();
                                                      alertDialog.show();

                                                  }
                                              }
        );

        //Código para permissao do armazenamento interno
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1000);
        }



    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults){
        switch (requestCode){
            case 1000:
                if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    Toast.makeText(this, "Permissão concedida", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(this, "Permissão negada", Toast.LENGTH_SHORT).show();
                    finish();
                }
        }
    }

    public void irTelaListagemMutiroes(View view){
        //Intent intent1 = new Intent(getApplicationContext(), CadastroMutiraoActivity.class);
        Intent intent1 = new Intent(getApplicationContext(), ListagemMutiroes.class);

        startActivity(intent1);
    }

    public void irTelaListagemListaNegra(View view){
        Intent intent1 = new Intent(getApplicationContext(), ListagemListaNegra.class);

        startActivity(intent1);
    }

}
