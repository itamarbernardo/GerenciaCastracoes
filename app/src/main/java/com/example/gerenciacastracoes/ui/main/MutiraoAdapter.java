package com.example.gerenciacastracoes.ui.main;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.gerenciacastracoes.R;
import com.example.gerenciacastracoes.negocio.entidades.Animal;
import com.example.gerenciacastracoes.negocio.entidades.Cliente;
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
        TextView quantidadeAnimais = (TextView) rowView.findViewById(R.id.txtQuantidadeAnimais);
        TextView quantidadeListaEspera = (TextView) rowView.findViewById(R.id.txtQntListaEspera);
        ImageView imagem = (ImageView) rowView.findViewById(R.id.imageView);
        TextView quantidadeRoupinhas = (TextView) rowView.findViewById(R.id.txtQuantidadeRoupinhas);

        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        int contAnimais = 0;
        int contRoupinhas = 0;

        List<Cliente> clientes = elementos.get(position).getClientes();
        if(clientes != null && clientes.size() != 0) {
            for (Cliente c : clientes) {
                contAnimais = contAnimais + c.getAnimais().size();

                for(Animal animal : c.getAnimais()){
                    if(animal.isQuerRoupinha()){
                        contRoupinhas++;
                    }
                }


            }
        }

        int contListaEspera = 0;
        List<Cliente> listaEspera = elementos.get(position).getListaEspera();
        if(listaEspera != null && listaEspera.size() != 0) {
            for (Cliente c : listaEspera) {
                contListaEspera = contListaEspera + c.getAnimais().size();
            }
        }

        dataMutirao.setText(elementos.get(position).getData().format(formato)); //Tenho que formatar a data para mostrar.
        quantidadeAnimais.setText(contAnimais + "");
        quantidadeListaEspera.setText(contListaEspera + "");
        quantidadeRoupinhas.setText(contRoupinhas + "");

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
