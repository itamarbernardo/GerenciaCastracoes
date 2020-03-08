package com.example.gerenciacastracoes;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.example.gerenciacastracoes.negocio.entidades.Animal;
import com.example.gerenciacastracoes.negocio.entidades.Cliente;
import com.example.gerenciacastracoes.negocio.entidades.Mutirao;
import com.example.gerenciacastracoes.negocio.exceccoes.mutirao.MutiraoNaoExisteException;
import com.example.gerenciacastracoes.negocio.fachada.Castracoes;
import com.example.gerenciacastracoes.ui.main.MutiraoAdapter;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.FileProvider;

import android.os.Environment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

public class ListagemMutiroes extends AppCompatActivity {

    private static final String TAG = "ListagemMutiroes";

    private Castracoes fachada = Castracoes.getFachada();
    private Toolbar toolbar;
    private SwipeMenuListView listaMutiroes;
    private ArrayList<Mutirao> mutiroes;
    private Font fonteNormal;
    private Font fonteNegrito;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listagem_mutiroes);

        listaMutiroes = (SwipeMenuListView) findViewById(R.id.listViewMutiroes);

        inicializaToolbar();

        mutiroes = (ArrayList<Mutirao>) fachada.listagemMutiroes();

        //Aqui dá pra colocar uma imagem de fundo se não houver mutiroes para mostrar e quando houver
        //só alterar a visibilidade da imagem
        if(mutiroes != null){

            fonteNormal = new Font(Font.FontFamily.UNDEFINED, 12, Font.NORMAL);
            fonteNegrito = new Font(Font.FontFamily.UNDEFINED, 12,Font.BOLD);

            configuraAdapterMutirao();
            configuraListView();
        }

    }

    public void configuraAdapterMutirao(){

        ArrayAdapter adapter = new MutiraoAdapter(this, mutiroes);
        listaMutiroes.setAdapter(adapter);

    }

    public void configuraListView(){


        SwipeMenuCreator creator = new SwipeMenuCreator() {
            @Override
            public void create(SwipeMenu menu) {
                SwipeMenuItem openItem = new SwipeMenuItem(
                        getApplicationContext());
                openItem.setBackground(new ColorDrawable(Color.rgb(0xC9, 0xC9,
                        0xCE)));
                openItem.setWidth(ClasseUtilitaria.dp2px(getApplicationContext(), 90));
                openItem.setIcon(R.drawable.ic_export_50);
                openItem.setTitleColor(Color.WHITE);

                menu.addMenuItem(openItem);

                SwipeMenuItem deleteItem = new SwipeMenuItem(
                        getApplicationContext());
                deleteItem.setBackground(new ColorDrawable(Color.rgb(0xF9,
                        0x3F, 0x25)));
                deleteItem.setWidth(ClasseUtilitaria.dp2px(getApplicationContext(), 90));
                deleteItem.setIcon(R.drawable.ic_delete_black_24dp);

                menu.addMenuItem(deleteItem);
            }
        };


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
                        // export
                        //Toast.makeText(getApplicationContext(), "Exportar", Toast.LENGTH_SHORT).show();
                        Intent sendIntent = new Intent();
                        sendIntent.setAction(Intent.ACTION_SEND);

                        String caminhoPdf = gerarPdf(mutiroes.get(position));

                        //sendIntent.putExtra(Intent.EXTRA_TEXT, gerarPdf(mutiroes.get(position)).toString());
                        File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), "/Gerencia_Castracoes/PDFGerados/"+ caminhoPdf);

                        //criar o intent para visualização do documento
                        Uri caminho = FileProvider.getUriForFile(ListagemMutiroes.this, getBaseContext().getApplicationContext().getPackageName() + ".com.example.gerenciacastracoes.provider", file);

                        //sendIntent.setDataAndType(caminho, "application/pdf");
                        sendIntent.setType("application/pdf");
                        sendIntent.putExtra(Intent.EXTRA_STREAM, caminho);

                        sendIntent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                        startActivity(sendIntent);


                        //intentCamera.putExtra(MediaStore.EXTRA_OUTPUT, uri);


                        break;
                    case 1:
                        // delete
                        removerMutirao(mutiroes.get(position).getCodigo(), position);
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

    public String gerarPdf(Mutirao mutirao){
        String nomeArquivo =  "Mutirao_" + mutirao.getTipo() + "_" + ClasseUtilitaria.converterDataParaStringFormatoTracinho(mutirao.getData()) + ".pdf";

        File path = new File(Environment.getExternalStorageDirectory(), "/Gerencia_Castracoes/PDFGerados");
        if(!path.exists()){
            path.mkdir();
        }

        File pdfFile = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), "/Gerencia_Castracoes/PDFGerados/" + nomeArquivo);

        Document doc = new Document();


        try {
            PdfWriter.getInstance(doc, new FileOutputStream(pdfFile));
            doc.open();

            Paragraph p = new Paragraph("Relatório Mutirão", new Font(Font.FontFamily.HELVETICA, 18, Font.BOLD));
            p.setAlignment(Paragraph.ALIGN_CENTER);
            doc.add(p);

            p = new Paragraph(" ");
            doc.add(p);

            p = new Paragraph("Data do Mutirão: ", fonteNegrito);
            p.setFont(fonteNormal);
            p.add(new Chunk(ClasseUtilitaria.converterDataParaString(mutirao.getData())));
            doc.add(p);

            p = new Paragraph("Tipo: ", fonteNegrito);
            p.setFont(fonteNormal);
            p.add(new Chunk(mutirao.getTipo()));
            doc.add(p);

            p = new Paragraph(" "); //Pula linha
            doc.add(p);

            preencherDadosClientes("Clientes", doc, mutirao.getClientes());
            preencherDadosClientes("Clientes Lista de espera", doc, mutirao.getListaEspera());

            doc.close();

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        }

        //return doc;
        return nomeArquivo;

    }

    public void preencherDadosClientes(String titulo, Document doc, List<Cliente> clientes) {
        try {
            Paragraph p = new Paragraph(titulo, new Font(Font.FontFamily.HELVETICA, 14, Font.BOLD));
            p.setAlignment(Paragraph.ALIGN_CENTER);
            doc.add(p);

            for (Cliente c : clientes) {
                String pagou;
                if (c.isPagou()) {
                    pagou = "Sim";
                } else {
                    pagou = "Não";
                }

                p = new Paragraph("Nome: ", fonteNegrito);
                p.setFont(fonteNormal);
                p.add(new Chunk(c.getNome()));

                doc.add(p);

                p = new Paragraph("Telefone: ", fonteNegrito);
                p.setFont(fonteNormal);
                p.add(new Chunk(c.getTelefone()));
                doc.add(p);

                p = new Paragraph("Tipo de Pagamento: ", fonteNegrito);
                p.setFont(fonteNormal);
                p.add(new Chunk(c.getTipoDePagamento()));
                doc.add(p);

                p = new Paragraph("Pagou?: ", fonteNegrito);
                p.setFont(fonteNormal);
                p.add(new Chunk(pagou));
                doc.add(p);

                p = new Paragraph(" "); //Pula uma linha
                doc.add(p);

                preencherTabelaAnimais(doc, c.getAnimais(), c.getNome());

            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void preencherTabelaAnimais(Document doc, List<Animal> animais, String nomeCliente) {
        try {
            PdfPTable titulo = new PdfPTable(1);
            PdfPCell celulaTitulo = new PdfPCell(new Paragraph("Animais de " + nomeCliente));
            celulaTitulo.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
            celulaTitulo.setExtraParagraphSpace(7);
            titulo.addCell(celulaTitulo);


            doc.add(titulo);

            PdfPTable tabela = new PdfPTable(6);

            PdfPCell celulaNomeAnimal = new PdfPCell(new Paragraph("Nome", fonteNegrito));
            PdfPCell celulaTipo = new PdfPCell(new Paragraph("Tipo", fonteNegrito));
            PdfPCell celulaSexo = new PdfPCell(new Paragraph("Sexo", fonteNegrito));
            PdfPCell celulaRaca = new PdfPCell(new Paragraph("Raça", fonteNegrito));
            PdfPCell celulaPelagem = new PdfPCell(new Paragraph("Pelagem", fonteNegrito));
            PdfPCell celulaQuerRoupinha = new PdfPCell(new Paragraph("Quer roupinha?", fonteNegrito));

            tabela.addCell(celulaNomeAnimal); //Testar para: tabela.addCell(//Passando uma tabela com os animais);
            tabela.addCell(celulaTipo);
            tabela.addCell(celulaSexo);
            tabela.addCell(celulaRaca);
            tabela.addCell(celulaPelagem);
            tabela.addCell(celulaQuerRoupinha);

            for (Animal a : animais) {

                String querRoupinha;

                if (a.isQuerRoupinha()) {
                    querRoupinha = "Sim";
                } else {
                    querRoupinha = "Não";
                }

                celulaNomeAnimal = new PdfPCell(new Paragraph(a.getNome()));
                celulaTipo = new PdfPCell(new Paragraph(a.getTipo()));
                celulaSexo = new PdfPCell(new Paragraph(a.getSexo() + ""));
                celulaRaca = new PdfPCell(new Paragraph(a.getRaca()));
                celulaPelagem = new PdfPCell(new Paragraph(a.getPelagem()));
                celulaQuerRoupinha = new PdfPCell(new Paragraph(querRoupinha));

                tabela.addCell(celulaNomeAnimal);
                tabela.addCell(celulaTipo);
                tabela.addCell(celulaSexo);
                tabela.addCell(celulaRaca);
                tabela.addCell(celulaPelagem);
                tabela.addCell(celulaQuerRoupinha);

            }

            doc.add(tabela);

            Paragraph p = new Paragraph(" ");
            doc.add(p);

        } catch (DocumentException ex) {
            Toast.makeText(getApplicationContext(), "Erro ao gerar o documento!", Toast.LENGTH_LONG).show();
            ex.printStackTrace();
        }

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

    public void removerMutirao(final int codigoMutirao, final int positionMutirao){
        AlertDialog.Builder alerta = new AlertDialog.Builder(this);
        alerta.setTitle("Remover...");
        alerta.setCancelable(false); //Se tiver true, permite que a caixa de dialogo suma se clicar fora da caixa de texto.
        alerta.setIcon(R.mipmap.ic_delete);
        alerta.setMessage("Tem certeza que deseja excluir este mutirão?");
        alerta.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                try {
                    fachada.removerMutirao(codigoMutirao);
                    ClasseUtilitaria.emitirAlerta(ListagemMutiroes.this, "Mutirão excluído com sucesso!");

                    mutiroes.remove(positionMutirao);
                    configuraAdapterMutirao();

                } catch (MutiraoNaoExisteException e) {
                    Toast.makeText(ListagemMutiroes.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }
        });
        alerta.setNegativeButton("Não", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        alerta.show();

    }
}
