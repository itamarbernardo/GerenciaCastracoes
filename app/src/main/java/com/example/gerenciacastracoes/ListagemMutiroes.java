package com.example.gerenciacastracoes;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.example.gerenciacastracoes.negocio.entidades.Mutirao;
import com.example.gerenciacastracoes.negocio.fachada.Castracoes;
import com.example.gerenciacastracoes.ui.main.MutiraoAdapter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class ListagemMutiroes extends AppCompatActivity {

    private static final String TAG = "ListagemMutiroes";

    private Castracoes fachada = Castracoes.getFachada();
    private Toolbar toolbar;
    private SwipeMenuListView listaMutiroes;
    private ArrayList<Mutirao> mutiroes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listagem_mutiroes);


        inicializaToolbar();

        mutiroes = (ArrayList<Mutirao>) fachada.listagemMutiroes();

        //Aqui dá pra colocar uma imagem de fundo se não houver mutiroes para mostrar e quando houver
        //só alterar a visibilidade da imagem
        if(mutiroes != null){
            inicializaEConfiguraListView();
        }

    }

    public void inicializaEConfiguraListView(){
        listaMutiroes = (SwipeMenuListView) findViewById(R.id.listViewMutiroes);

        SwipeMenuCreator creator = new SwipeMenuCreator() {
            @Override
            public void create(SwipeMenu menu) {
                SwipeMenuItem openItem = new SwipeMenuItem(
                        getApplicationContext());
                // set item background
                openItem.setBackground(new ColorDrawable(Color.rgb(0xC9, 0xC9,
                        0xCE)));
                // set item width
                openItem.setWidth(ClasseUtilitaria.dp2px(getApplicationContext(), 90));
                // set item title
                openItem.setTitle("Export");
                // set item title fontsize
                openItem.setTitleSize(18);
                // set item title font color
                openItem.setTitleColor(Color.WHITE);
                // add to menu
                menu.addMenuItem(openItem);

                // create "delete" item
                SwipeMenuItem deleteItem = new SwipeMenuItem(
                        getApplicationContext());
                // set item background
                deleteItem.setBackground(new ColorDrawable(Color.rgb(0xF9,
                        0x3F, 0x25)));
                // set item width
                deleteItem.setWidth(ClasseUtilitaria.dp2px(getApplicationContext(), 90));
                // set a icon
                deleteItem.setIcon(R.mipmap.ic_delete);
                // add to menu
                menu.addMenuItem(deleteItem);
            }
        };

        ArrayAdapter adapter = new MutiraoAdapter(this, mutiroes);
        listaMutiroes.setAdapter(adapter);

        listaMutiroes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText(getApplicationContext(), "Código: " + mutiroes.get(position).getCodigo(), Toast.LENGTH_SHORT).show();
                irTelaVisualizarMutirao(view, mutiroes.get(position).getCodigo());
            }
        });


        listaMutiroes.setMenuCreator(creator);
        listaMutiroes.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                switch (index) {
                    case 0:
                        // open
                        Toast.makeText(getApplicationContext(), "Abrir", Toast.LENGTH_SHORT).show();
                        break;
                    case 1:
                        // delete
                        Toast.makeText(getApplicationContext(), "Delete", Toast.LENGTH_SHORT).show();
                        break;
                }
                // false : close the menu; true : not close the menu
                return false;
            }
        });
    }

    public void inicializaToolbar(){
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    public void irTelaVisualizarMutirao(View v, int codigo){
        Intent intent = new Intent(getApplicationContext(), VisualizarMutirao.class);
        Bundle parametos = new Bundle();
        parametos.putInt("codigo_mutirao", codigo);

        intent.putExtras(parametos);
        startActivity(intent);
    }

    public void irTelaCadastro(View view) {
        Intent intent1 = new Intent(getApplicationContext(), CadastroMutirao.class);
        startActivity(intent1);
    }
}
