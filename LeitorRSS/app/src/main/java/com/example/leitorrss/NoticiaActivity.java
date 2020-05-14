package com.example.leitorrss;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentUris;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.webkit.WebView;
import android.widget.TextView;

import android.text.format.DateFormat;

import java.net.URI;
//import java.text.DateFormat;

public class NoticiaActivity extends AppCompatActivity {
    private TextView titulo;
    private TextView data;
    private WebView conteudo;
    Cursor cursor;
    int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.noticia);
        setTitle("Noticias");
        titulo = (TextView) findViewById(R.id.feedTitulo);
        //ao premir o titulo a janela fecha
        titulo.setOnClickListener( (v) -> finish());
        data = (TextView) findViewById(R.id.feedData);
        conteudo = (WebView) findViewById(R.id.feedConteudo);
    }

    @Override
    protected void onStart() {
        super.onStart();
        try{
            Bundle extras = getIntent().getExtras();
            long idNoticia = extras.getLong("idNoticia");
            id = (int) idNoticia;
            final String[] colunas = new String[]{
                    FeedsDB.Posts._ID,
                    FeedsDB.Posts.TITLE,
                    FeedsDB.Posts.PUB_DATE,
                    FeedsDB.Posts.DESCRIPTION,
                    FeedsDB.Posts.LINK
            };
            Uri uri = Uri.parse("content://pplware.sapo.pt/feed/");
            uri = ContentUris.withAppendedId(uri, idNoticia);
            //Query managed: atividade se encarrega de fechar e voltar
            //a carregar o cursor quando necessário
            cursor = managedQuery(uri, colunas, null, null,
                    FeedsDB.Posts.PUB_DATE + "DESC");
            //SABER SE OS DADOS ALTERARAM PARA RECARREGAR CURSOR
            cursor.setNotificationUri(getContentResolver(), uri);
            //para que a atividade se encarregue de manusear o cursor conforme seus ciclos de vida
            startManagingCursor(cursor);
            //mostramos os dados do cursor na vista
            if(cursor.moveToPosition(id-1)){
                titulo.setText(cursor.getString(1));
                java.text.DateFormat dateFormat = DateFormat
                        .getLongDateFormat(NoticiaActivity.this);
                data.setText(dateFormat.format(cursor.getLong(2)));
                String link=cursor.getString(4);
                conteudo.loadUrl(link);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
