package com.example.gerenciacastracoes.ui.main;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.gerenciacastracoes.R;
import com.example.gerenciacastracoes.negocio.entidades.Animal;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class AnimalAdapter extends ArrayAdapter<Animal> {

        private final Context context;
        private final List<Animal> elementos;

        public AnimalAdapter(Context context, ArrayList<Animal> elementos) {
            super(context, R.layout.linha_animal, elementos);
            this.context = context;
            this.elementos = elementos;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View rowView = inflater.inflate(R.layout.linha_animal, parent, false); //Trocar o layout

            ConstraintLayout layoutLinhaAnimal = (ConstraintLayout) rowView.findViewById(R.id.layoutLinhaAnimal);
            TextView nome = (TextView) rowView.findViewById(R.id.txtNome);
            TextView tipo = (TextView) rowView.findViewById(R.id.txtTipo);
            TextView sexo = (TextView) rowView.findViewById(R.id.txtSexo);
            TextView pelagem = (TextView) rowView.findViewById(R.id.txtPelagem);
            TextView raca = (TextView) rowView.findViewById(R.id.txtRaca);
            TextView querRoupinha = (TextView) rowView.findViewById(R.id.txtQuerRoupinha);

            nome.setText(elementos.get(position).getNome());
            tipo.setText(elementos.get(position).getTipo());
            sexo.setText(elementos.get(position).getSexo() + "");
            pelagem.setText(elementos.get(position).getPelagem());
            raca.setText(elementos.get(position).getRaca());

            if(elementos.get(position).getSexo() == 'M'){
                layoutLinhaAnimal.setBackgroundResource(R.color.colorMacho);
            }else{
                layoutLinhaAnimal.setBackgroundResource(R.color.colorFemea);
            }

            boolean roupinha = elementos.get(position).isQuerRoupinha();
            if(roupinha) {
                querRoupinha.setText("Sim");
            }else{
                querRoupinha.setText("Não");
            }

            return rowView;
        }


}
