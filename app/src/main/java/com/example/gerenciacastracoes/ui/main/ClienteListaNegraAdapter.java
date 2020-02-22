package com.example.gerenciacastracoes.ui.main;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.TextView;

import com.example.gerenciacastracoes.R;
import com.example.gerenciacastracoes.negocio.entidades.Cliente;

import java.util.ArrayList;
import java.util.List;

public class ClienteListaNegraAdapter extends BaseAdapter {

    private List<Cliente> clientes;
    private List<Cliente> clientesPesquisa;
    private LayoutInflater layoutInflater;

    public ClienteListaNegraAdapter(Context context, ArrayList<Cliente> clientes) {
        this.clientes = clientes;
        this.clientesPesquisa = clientes;
        this.layoutInflater = LayoutInflater.from(context);
    }


    @Override
    public int getCount() {
        return clientesPesquisa.size();
    }

    @Override
    public Cliente getItem(int arg0) {
        return clientesPesquisa.get(arg0);
    }

    @Override
    public long getItemId(int arg0) {
        return clientesPesquisa.get(arg0).getCodigo();
    }


    public View getView(int position, View convertView, ViewGroup parent) {
        ItensCliente itemCliente = new ItensCliente();
        Cliente objeto = clientesPesquisa.get(position);

        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.linha_cliente_lista_negra, null);
            itemCliente.nome = (TextView) convertView.findViewById(R.id.txtNome);
            itemCliente.telefone = (TextView) convertView.findViewById(R.id.txtTelefone);
            convertView.setTag(itemCliente);
        } else {
            itemCliente = (ItensCliente) convertView.getTag();
        }

        itemCliente.nome.setText(objeto.getNome());
        itemCliente.telefone.setText(objeto.getTelefone());

        return convertView;

    }

    public Filter getFilter() {
        Filter filter = new Filter() {

            @Override
            protected FilterResults performFiltering(CharSequence filtro) {
                FilterResults results = new FilterResults();
                //se não foi realizado nenhum filtro insere todos os itens.
                if (filtro == null || filtro.length() == 0) {
                    results.count = clientes.size();
                    results.values = clientes;
                } else {
                    //cria um array para armazenar os objetos filtrados.
                    List<Cliente> itens_filtrados = new ArrayList<Cliente>();

                    //percorre toda lista verificando se contem a palavra do filtro na descricao do objeto.
                    for (int i = 0; i < clientes.size(); i++) {
                        Cliente data = clientes.get(i);

                        filtro = filtro.toString().toLowerCase();
                        String condicao = data.getNome().toLowerCase() +  data.getTelefone().replaceAll("[^0-9]", "");

                        if (condicao.contains(filtro)) {
                            //se conter adiciona na lista de itens filtrados.
                            itens_filtrados.add(data);
                        }
                    }
                    // Define o resultado do filtro na variavel FilterResults
                    results.count = itens_filtrados.size();
                    results.values = itens_filtrados;
                }
                return results;
            }

            @SuppressWarnings("unchecked")
            @Override
            protected void publishResults(CharSequence constraint, Filter.FilterResults results) {
                clientesPesquisa = (List<Cliente>) results.values; // Valores filtrados.
                notifyDataSetChanged();  // Notifica a lista de alteração
            }

        };
        return filter;
    }

    private class ItensCliente {

        TextView nome;
        TextView telefone;
    }

}
