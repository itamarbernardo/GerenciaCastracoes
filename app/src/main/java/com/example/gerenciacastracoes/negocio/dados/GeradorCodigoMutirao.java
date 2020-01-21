package com.example.gerenciacastracoes.negocio.dados;

import android.os.Environment;

import com.example.gerenciacastracoes.negocio.entidades.Mutirao;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;

public class GeradorCodigoMutirao {

    public static Integer gerarCodigo() {

        String nomeArquivo = "codigoMutirao.dat";

        Integer codigoInterno = null;
        Integer codigo;

        File folder = new File(Environment.getExternalStorageDirectory() + "/Gerencia_Castracoes");
        if(!folder.exists()){
            folder.mkdir();
        }
        File arquivo = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), "/Gerencia_Castracoes/" + nomeArquivo);

            //Leitura
        try {
            FileInputStream file = new FileInputStream(arquivo);
            ObjectInputStream is = new ObjectInputStream(file);
            codigoInterno = (Integer) is.readObject();
            is.close();
        } catch (FileNotFoundException ex) {
            codigoInterno = 0;
            System.out.println(ex.getMessage());
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        } catch (ClassNotFoundException ex) {
            System.out.println(ex.getMessage());
        }

        codigo = codigoInterno;
        //Escrita
        if(codigoInterno != null) {
            try {
                FileOutputStream file2 = new FileOutputStream(arquivo);
                ObjectOutputStream os = new ObjectOutputStream(file2);
                codigoInterno++;
                os.writeObject(codigoInterno);
                os.close();
            } catch (FileNotFoundException ex) {
                System.out.println(ex.getMessage());
            } catch (IOException ex) {
                System.out.println(ex.getMessage());

            }
        }

        return codigo;
    }
}
