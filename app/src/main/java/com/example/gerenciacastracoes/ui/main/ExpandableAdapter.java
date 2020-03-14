package com.example.gerenciacastracoes.ui.main;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
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

    private List<Cliente> listGroupPesquisa;

    private LayoutInflater inflater;

    public ExpandableAdapter(Context context, List<Cliente> listGroup) {
        this.listGroupCliente = new ArrayList<>();
        this.listGroupCliente.addAll(listGroup);
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        this.listGroupPesquisa = new ArrayList<>();
        this.listGroupPesquisa.addAll(listGroup);

    }

    @Override
    public int getGroupCount() {
        return listGroupPesquisa.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return listGroupPesquisa.get(groupPosition).getAnimais().size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return listGroupPesquisa.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return listGroupPesquisa.get(groupPosition).getAnimais().get(childPosition);
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

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.header_expandable_list_view, null);
            holder = new ViewHolderGroup();
            convertView.setTag(holder);


            holder.txtGroupNome = (TextView) convertView.findViewById(R.id.txtGroupNome);
            holder.txtGroupTelefone = (TextView) convertView.findViewById(R.id.txtGroupTelefone);
            holder.txtGroupQuantidadeRoupinhas = (TextView) convertView.findViewById(R.id.txtQuantidadeRoupinhas);

        } else {
            holder = (ViewHolderGroup) convertView.getTag();
        }

        int quantRoupinhas = 0;

        for (Animal animal : listGroupPesquisa.get(groupPosition).getAnimais()) {
            if (animal.isQuerRoupinha()) {
                quantRoupinhas++;
            }
        }


        holder.txtGroupNome.setText(listGroupPesquisa.get(groupPosition).getNome());
        holder.txtGroupTelefone.setText(listGroupPesquisa.get(groupPosition).getTelefone());
        holder.txtGroupQuantidadeRoupinhas.setText(quantRoupinhas + "");

        boolean pagou = listGroupPesquisa.get(groupPosition).isPagou();

        if(!pagou){
            holder.txtGroupNome.setTextColor(Color.RED);
            holder.txtGroupTelefone.setTextColor(Color.RED);
            holder.txtGroupQuantidadeRoupinhas.setTextColor(Color.RED);

        }else{
            holder.txtGroupNome.setTextColor(Color.GREEN);
            holder.txtGroupTelefone.setTextColor(Color.GREEN);
            holder.txtGroupQuantidadeRoupinhas.setTextColor(Color.GREEN);

        }

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ViewHolderItem holder;

        Animal animal = (Animal) getChild(groupPosition, childPosition);

        if (convertView == null) {
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
        } else {
            holder = (ViewHolderItem) convertView.getTag();
        }

        String querRoupinha = "Não";
        if (animal.isQuerRoupinha()) {
            querRoupinha = "Sim";
        }

        if (animal.getSexo() == 'M') {
            holder.telaFundo.setBackgroundResource(R.color.colorMacho);
        } else {
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


    public void filterData(String query) {

        //ContinnentList é listaPesquisa
        //Original List é listaClientes

        query = query.toLowerCase();
        Log.d("MyListAdapter", "Clientes Total: " + listGroupCliente.size() + "\nTotal pesquisa: " + listGroupPesquisa.size());
        listGroupPesquisa.clear();

        if (query.equals("")) {
            listGroupPesquisa.addAll(listGroupCliente);
        } else {

            for (Cliente cliente : listGroupCliente) {
                String pesquisa = cliente.getNome().toLowerCase() + cliente.getTelefone().replaceAll("[^0-9]", "");;
                Log.v("MyListAdapter", cliente.toString());
                if (pesquisa.contains(query)) {
                    listGroupPesquisa.add(cliente);
                }

            }
        }

        Log.v("MyListAdapter", "Itens achados: " + listGroupPesquisa.size());
        notifyDataSetChanged();

    }


    class ViewHolderGroup {
        TextView txtGroupNome;
        TextView txtGroupTelefone;
        TextView txtGroupQuantidadeRoupinhas;

    }

    class ViewHolderItem {
        TextView txtItemNome;
        TextView txtItemTipo;
        TextView txtItemSexo;
        TextView txtItemRaca;
        TextView txtItemPelagem;
        TextView txtQuerRoupinha;
        ConstraintLayout telaFundo;

    }
}
