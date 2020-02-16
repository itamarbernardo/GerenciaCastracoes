package com.example.gerenciacastracoes.ui.main;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.gerenciacastracoes.R;
import com.example.gerenciacastracoes.negocio.entidades.Cliente;

import java.util.ArrayList;
import java.util.List;

public class ClienteListaNegraAdapter extends ArrayAdapter<Cliente> {

    private final Context context;
    private final List<Cliente> clientes;

    public ClienteListaNegraAdapter(Context context, ArrayList<Cliente> clientes) {
        super(context, R.layout.linha_cliente_lista_negra, clientes);
        this.context = context;
        this.clientes = clientes;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.linha_cliente_lista_negra, parent, false);

        TextView nome = (TextView) rowView.findViewById(R.id.txtNome);
        TextView telefone = (TextView) rowView.findViewById(R.id.txtTelefone);


        nome.setText(clientes.get(position).getNome());
        telefone.setText(clientes.get(position).getTelefone());


        return rowView;
    }

}
