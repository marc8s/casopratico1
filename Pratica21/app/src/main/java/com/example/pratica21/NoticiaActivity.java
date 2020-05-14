package com.example.pratica21;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentUris;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.TextView;
import android.text.format.DateFormat;



public class NoticiaActivity extends AppCompatActivity {
    private TextView titulo;
    private TextView data;
    private WebView conteudo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_noticia);
        setTitle(R.string.titulo_noticias);

        titulo = findViewById(R.id.feedTitulo);
        titulo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        data = findViewById(R.id.feedData);
        conteudo = findViewById(R.id.feedConteudo);
        conteudo.getSettings().setLoadWithOverviewMode(true);
        conteudo.getSettings().setUseWideViewPort(true);
    }

    @Override
    protected void onStart() {
        super.onStart();

        try{
            Bundle extras = getIntent().getExtras();
            long idNoticia = extras.getLong("idNoticia");
            final String[] columnas = new String[]{
                    FeedsDB.Posts._ID, FeedsDB.Posts.TITLE, FeedsDB.Posts.PUB_DATE, FeedsDB.Posts.LINK
            };
            Uri uri = FeedsProvider.CONTENT_URI;
            uri = ContentUris.withAppendedId(uri, idNoticia);
            Cursor cursor = managedQuery(uri, columnas, null, null, FeedsDB.Posts.PUB_DATE + " DESC");
            cursor.setNotificationUri(getContentResolver(), uri);
            startManagingCursor(cursor);
            int id= (int) idNoticia;
            if(cursor.moveToPosition(id-1)){
                titulo.setText(cursor.getString(1));
                java.text.DateFormat dateFormat = DateFormat.getLongDateFormat(NoticiaActivity.this);
                data.setText(dateFormat.format(cursor.getLong(2)));
                String texto = new String(cursor.getString(3).getBytes(), "utf-8");
                String link = cursor.getString(3);
                conteudo.loadUrl(link);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
