package com.example.gerenciacastracoes.ui.main;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Filter;
import android.widget.TextView;


import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.gerenciacastracoes.R;
import com.example.gerenciacastracoes.negocio.entidades.Animal;
import com.example.gerenciacastracoes.negocio.entidades.Cliente;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ExpandableAdapter extends BaseExpandableListAdapter {
    private List<Cliente> listGroupCliente;
    private HashMap<Cliente, List<Animal>> listData;

    private List<Cliente> listGroupPesquisa;
    private HashMap<Cliente, List<Animal>> listDataPesquisa;

    private LayoutInflater inflater;

    public ExpandableAdapter(Context context, List<Cliente> listGroup, HashMap<Cliente, List<Animal>> listData) {
        this.listGroupCliente = listGroup;
        this.listData = listData;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.listGroupPesquisa = listGroup;
        this.listDataPesquisa = listData;
    }

    @Override
    public int getGroupCount() {
        return listGroupCliente.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return listData.get(listGroupCliente.get(groupPosition)).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return listGroupCliente.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return listData.get(listGroupCliente.get(groupPosition)).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        ViewHolderGroup holder;

        if(convertView == null){
            convertView = inflater.inflate(R.layout.header_expandable_list_view, null);
            holder = new ViewHolderGroup();
            convertView.setTag(holder);


            holder.txtGroupNome = (TextView) convertView.findViewById(R.id.txtGroupNome);
            holder.txtGroupTelefone = (TextView) convertView.findViewById(R.id.txtGroupTelefone);
            holder.txtGroupQuantidadeRoupinhas = (TextView) convertView.findViewById(R.id.txtQuantidadeRoupinhas);

        }
        else{
            holder = (ViewHolderGroup) convertView.getTag();
        }

        int quantRoupinhas = 0;
        for(Animal animal : listGroupCliente.get(groupPosition).getAnimais()){
            if(animal.isQuerRoupinha()){
                quantRoupinhas++;
            }
        }

        holder.txtGroupNome.setText(listGroupCliente.get(groupPosition).getNome());
        holder.txtGroupTelefone.setText(listGroupCliente.get(groupPosition).getTelefone());
        holder.txtGroupQuantidadeRoupinhas.setText(quantRoupinhas + "");


        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ViewHolderItem holder;

        Animal animal = (Animal) getChild(groupPosition, childPosition);

        if(convertView == null){
            convertView = inflater.inflate(R.layout.item_expandable_list_view, null);
            holder = new ViewHolderItem();
            convertView.setTag(holder);

            holder.txtItemNome = (TextView) convertView.findViewById(R.id.txtItemNome);
            holder.txtItemTipo = (TextView) convertView.findViewById(R.id.txtItemTipo);
            holder.txtItemSexo = (TextView) convertView.findViewById(R.id.txtItemSexo);
            holder.txtItemRaca = (TextView) convertView.findViewById(R.id.txtItemRaca);
            holder.txtItemPelagem = (TextView) convertView.findViewById(R.id.txtItemPelagem);
            holder.txtQuerRoupinha = (TextView) convertView.findViewById(R.id.txtQuerRoupinha);
            holder.telaFundo = (ConstraintLayout) convertView.findViewById(R.id.telaFundo);
        }
        else{
            holder = (ViewHolderItem) convertView.getTag();
        }

        String querRoupinha = "Não";
        if(animal.isQuerRoupinha()){
            querRoupinha = "Sim";
        }

        if(animal.getSexo() == 'M'){
            holder.telaFundo.setBackgroundResource(R.color.colorMacho);
        }else{
            holder.telaFundo.setBackgroundResource(R.color.colorFemea);
        }

        holder.txtItemNome.setText(animal.getNome());
        holder.txtItemTipo.setText(animal.getTipo());
        holder.txtItemSexo.setText(animal.getSexo() + "");
        holder.txtItemRaca.setText(animal.getRaca());
        holder.txtItemPelagem.setText(animal.getPelagem());
        holder.txtQuerRoupinha.setText(querRoupinha);

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    public Filter getFilter() {
        Filter filter = new Filter() {

            @Override
            protected FilterResults performFiltering(CharSequence filtro) {
                FilterResults results = new FilterResults();
                //se não foi realizado nenhum filtro insere todos os itens.
                if (filtro == null || filtro.length() == 0) {
                    results.count = listGroupCliente.size();
                    results.values = listGroupCliente;
                } else {
                    //cria um array para armazenar os objetos filtrados.
                    List<Cliente> itens_filtrados = new ArrayList<Cliente>();

                    //percorre toda lista verificando se contem a palavra do filtro na descricao do objeto.
                    for (int i = 0; i < listGroupCliente.size(); i++) {
                        Cliente data = listGroupCliente.get(i);

                        filtro = filtro.toString().toLowerCase();
                        String condicao = data.getNome().toLowerCase() +  data.getTelefone().replaceAll("[^0-9]", "");

                        if (condicao.contains(filtro)) {
                            //se conter adiciona na lista de itens filtrados.
                            //Se chegar aqui, é porque o cliente pertence. Então tem que pegar os animais dele!
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
                listGroupPesquisa = (List<Cliente>) results.values; // Valores filtrados.
                notifyDataSetChanged();  // Notifica a lista de alteração
            }

        };
        return filter;
    }

    class ViewHolderGroup{
        TextView txtGroupNome;
        TextView txtGroupTelefone;
        TextView txtGroupQuantidadeRoupinhas;

    }

    class ViewHolderItem{
        TextView txtItemNome;
        TextView txtItemTipo;
        TextView txtItemSexo;
        TextView txtItemRaca;
        TextView txtItemPelagem;
        TextView txtQuerRoupinha;
        ConstraintLayout telaFundo;

    }
}
