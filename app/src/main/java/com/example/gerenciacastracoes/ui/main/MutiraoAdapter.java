package com.example.gerenciacastracoes.ui.main;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.gerenciacastracoes.R;
import com.example.gerenciacastracoes.negocio.entidades.Mutirao;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class MutiraoAdapter extends ArrayAdapter<Mutirao> {

    private final Context context;
    private final List<Mutirao> elementos;

    public MutiraoAdapter(Context context, ArrayList<Mutirao> elementos) {
        super(context, R.layout.linha, elementos);
        this.context = context;
        this.elementos = elementos;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.linha, parent, false);

        TextView dataMutirao = (TextView) rowView.findViewById(R.id.txtData);
        TextView codigo = (TextView) rowView.findViewById(R.id.txtCodigo);
        TextView quantidadeClientes = (TextView) rowView.findViewById(R.id.txtQuantidadeClientes);
        TextView quantidadeListaEspera = (TextView) rowView.findViewById(R.id.txtQntListaEspera);
        ImageView imagem = (ImageView) rowView.findViewById(R.id.imageView);

        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        dataMutirao.setText(elementos.get(position).getData().format(formato)); //Tenho que formatar a data para mostrar.
        codigo.setText(elementos.get(position).getCodigo() + "");
        quantidadeClientes.setText(elementos.get(position).getClientes().size() + "");
        quantidadeListaEspera.setText(elementos.get(position).getListaEspera().size() + "");

        String tipo = elementos.get(position).getTipo();
        if(tipo.equals("Gato")) {
            imagem.setImageResource(R.drawable.gato);
        }else if(tipo.equals("Cachorro")){
            imagem.setImageResource(R.drawable.cachorro2);
        }else{
            imagem.setImageResource(R.drawable.misto);
        }

        return rowView;
    }
}
