package com.example.leitorrss;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ListActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import android.text.format.DateFormat;
//import java.text.DateFormat;

public class TitulosActivity extends ListActivity {
    //ATUALIZA A CADA HORA
    private static final long FREQUENCIA_ATUALIZACAO = 60 * 60 * 1000;
    //TIPO DE DIALOGO ACERCA DE
    private static final int DIALOG_ABOUT = 0;
    private  AtualizarPostAsyncTask tarefa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //SOLICITA QUE A JANELA POSSA MOSTRAR A BARRA DE PROGRESSO NO TITULO
        requestWindowFeature(Window.FEATURE_PROGRESS);
        setContentView(R.layout.feeds);
        setTitle("Noticias");
        //CARREGA A INFORMAÇÃO DA BASE DE DADOS NO OBJETO LISTVIEW
        configurarAdapter();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //CRIA E LANÇA A TAREFA ASSINCRONA DE ATUALIZAÇÃO
        carregarNoticias();
    }

    public void configurarAdapter(){
        //obtem os dados da base de dados
        final String[] colunas = new String[]{
            FeedsDB.Posts._ID, //0
            FeedsDB.Posts.TITLE, //1
            FeedsDB.Posts.FRASE, //2
            FeedsDB.Posts.PUB_DATE,//3
        };

        Uri uri = Uri.parse("content://pplware.sapo.pt/feed");
        Cursor cursor = managedQuery(uri, colunas, null, null,
                FeedsDB.Posts.PUB_DATE+ " DESC");
        //queremos saber se alteram os dados para recarregar o cursor
        cursor.setNotificationUri(getContentResolver(), uri);
        //atividade mansuea o cursor conforme o ciclo de vida
        startManagingCursor(cursor);

        //mapea querys do sql para os campos da vista
        String[] camposDb = new String[]{
            FeedsDB.Posts.TITLE,
            FeedsDB.Posts.FRASE,
            FeedsDB.Posts.PUB_DATE
        };
        int[] camposView = new int[]{
            R.id.feedTitulo, R.id.feedFrase, R.id.feedData
        };


        //criação do adapter
        SimpleCursorAdapter adapter = new SimpleCursorAdapter(this, R.layout.feeds_item,
                cursor, camposDb, camposView);
        //formatação da data
        final java.text.DateFormat dateFormat = DateFormat
                .getLongDateFormat(TitulosActivity.this);

        /*adapter.setViewBinder((view, cursor, columnIndex) -> {
            if (view.getId() == R.id.feedData) {
                long timestamp;
                timestamp = cursor.getLong(columnIndex);
                ((TextView) view).setText(dateFormat.format(timestamp));
                return true;
            } else {
                return false;
            }
        });*/




        //carrerga o adapter
        setListAdapter(adapter);
        

    }

    //classe interna asynctask
    class AtualizarPostAsyncTask extends AsyncTask<Void, Void, Void>{

        @Override
        protected void onPreExecute() {
            setBarraProgressoVisivel(true);
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {
            MasterdApplication app = (MasterdApplication) getApplication();
            RssDownloadHelper.updateRssData(app.getRssUrl(), getContentResolver());
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            SharedPreferences prefs = getPreferences(MODE_PRIVATE);
            SharedPreferences.Editor editor = prefs.edit();
            editor.putLong("ultima atualizacao", System.currentTimeMillis());
            editor.commit();
            setBarraProgressoVisivel(false);
        }

        @Override
        protected void onCancelled() {
            setBarraProgressoVisivel(false);
            //se cancelar, proxima vez que arrancar deve voltar a carregar
            SharedPreferences prefs = getPreferences(MODE_PRIVATE);
            SharedPreferences.Editor editor = prefs.edit();
            editor.putLong("ultima atualizacao", 0);
            editor.commit();
            super.onCancelled();
        }
    }


    //controle do tempo entre as recargas
    public void carregarNoticias(){
        SharedPreferences prefs = getPreferences(MODE_PRIVATE);
        long ultima = prefs.getLong("ultima atualizacao", 0);

        if((System.currentTimeMillis() - ultima) > FREQUENCIA_ATUALIZACAO){
            tarefa = new AtualizarPostAsyncTask();
            tarefa.execute();
        }
    }

    @Override
    protected void onStop() {
        //se estiver a ser executada uma tarefa em segundo plano esta é parada
        if (tarefa != null && !tarefa.getStatus().equals(AsyncTask.Status.FINISHED)){
            tarefa.cancel(true);
        }
        super.onStop();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menuprincipal, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.menuAcercaDe:
                showDialog(DIALOG_ABOUT);
                return true;
            case R.id.menuQuit:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id){
            case DIALOG_ABOUT:
                AlertDialog dialogAbout = null;
                final AlertDialog.Builder builder;
                LayoutInflater li = LayoutInflater.from(this);
                View view = li.inflate(R.layout.acercade, null);
                builder = new AlertDialog.Builder(this)
                        .setIcon(R.mipmap.ic_launcher)
                        .setTitle("ExemploUD13")
                        .setPositiveButton("Ok", null).setView(view);
                dialogAbout = builder.create();
                return dialogAbout;
            default:
                return null;
        }

    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        Intent i = new Intent();
        i.setClass(TitulosActivity.this, NoticiaActivity.class);
        i.putExtra("idNoticia", id);
        startActivity(i);
    }

    //metodo que permite apresentar e ocultar a barra de progresso
    public void setBarraProgressoVisivel(boolean visible){
        final Window window = getWindow();
        if(visible){
            window.setFeatureInt(Window.FEATURE_PROGRESS,
                    Window.PROGRESS_VISIBILITY_ON);
            window.setFeatureInt(Window.FEATURE_PROGRESS,
                    Window.PROGRESS_INDETERMINATE_ON);
        }else{
            window.setFeatureInt(Window.FEATURE_PROGRESS,
                    Window.PROGRESS_VISIBILITY_OFF);
        }
    }
}
