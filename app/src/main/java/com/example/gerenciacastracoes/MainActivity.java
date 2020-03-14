package com.example.gerenciacastracoes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private BottomNavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        apagarPdfsGerados();
        navigationView = (BottomNavigationView) findViewById(R.id.navigationView);
        navigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.navigation_inicio:
                        break;

                    case R.id.navigation_lista_mutiroes:
                        irTelaListagemMutiroes();
                        break;

                    case R.id.navigation_lista_negra:
                        irTelaListagemListaNegra();
                        break;

                }
                return false;
            }
        });

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
        Intent intent1 = new Intent(getApplicationContext(), ListagemMutiroes.class);

        startActivity(intent1);
    }

    public void irTelaListagemListaNegra(View view){
        Intent intent1 = new Intent(getApplicationContext(), ListagemListaNegra.class);

        startActivity(intent1);
    }

    public void irTelaListagemMutiroes(){
        Intent intent1 = new Intent(getApplicationContext(), ListagemMutiroes.class);

        startActivity(intent1);
    }

    public void irTelaListagemListaNegra(){
        Intent intent1 = new Intent(getApplicationContext(), ListagemListaNegra.class);

        startActivity(intent1);
    }

    public void apagarPdfsGerados() {
        File diretorio = new File(Environment.getExternalStorageDirectory(), "/Gerencia_Castracoes/PDFGerados");
        if (diretorio.isDirectory()) {
            File[] arquivos = diretorio.listFiles();
            for (File arquivo : arquivos) {
                arquivo.delete();
            }
        }
    }

    /**
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        switch (menuItem.getItemId()) {
            case R.id.navigation_inicio:
                break;

            case R.id.navigation_lista_mutiroes:
                Intent intent1 = new Intent(getApplicationContext(), ListagemListaNegra.class);
                startActivity(intent1);
                break;

            case R.id.navigation_lista_negra:
                Intent intent2 = new Intent(getApplicationContext(), ListagemListaNegra.class);
                startActivity(intent2);
                break;

        }
        return true;


    }**/

}
