package com.example.casopratico2;

import android.app.ListActivity;
import android.content.ContentUris;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;


public class Feed extends ListActivity{
    private static final long FREQUENCIA_ATUALIZACAO = 60*60*1000;
    private AtualizarPostAsyncTask tarefa;
    Cursor cursor;
    Uri uri;
    private int codigoFeed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_PROGRESS);
        setContentView(R.layout.activity_feed);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        codigoFeed = preferences.getInt("feedselecao", 0);
        configurarAdapter();
    }

    @Override
    protected void onResume() {
        super.onResume();
        carregarNoticias();
    }

    public void configurarAdapter(){
        final String[] colunas = new String[]{FeedsDB.Posts._ID,
                FeedsDB.Posts.TITLE,
                FeedsDB.Posts.PUB_DATE,};
        if(codigoFeed == 2){
            uri = Uri.parse("content://publico.pt/post");
        }
        if(codigoFeed == 3){
            uri = Uri.parse("content://tsf.pt/post");
        }
        if(codigoFeed == 4){
            uri = Uri.parse("content://cmjornal.pt/post");
        }
        cursor = managedQuery(uri, colunas, null, null, FeedsDB.Posts.PUB_DATE + " DESC");
        cursor.setNotificationUri(getContentResolver(), uri);
        startManagingCursor(cursor);

        String[] camposDb = new String[]{
                FeedsDB.Posts.TITLE
        };
        int[] camposView = new int[]{
                R.id.feedTitulo
        };

        SimpleCursorAdapter adapter = new SimpleCursorAdapter(this, R.layout.feed_item, cursor, camposDb, camposView, 0);
        if(cursor.getCount() == 0){
            Toast.makeText(getApplicationContext(), "Problema ao atualizar o feed, volte e tente novamente!", Toast.LENGTH_SHORT).show();
        }

        setListAdapter(adapter);

    }

    class AtualizarPostAsyncTask extends AsyncTask<Void, Void, Void>{
        @Override
        protected void onPreExecute() {
            setBarraProgressoVisivel(true);
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Void result) {
            SharedPreferences prefs = getPreferences(MODE_PRIVATE);
            SharedPreferences.Editor editor = prefs.edit();
            editor.putLong("ultima_atualizacao", System.currentTimeMillis());
            editor.commit();
            setBarraProgressoVisivel(false);
            //finish();
        }

        @Override
        protected void onCancelled() {
            setBarraProgressoVisivel(false);
            SharedPreferences prefs = getPreferences(MODE_PRIVATE);
            SharedPreferences.Editor editor = prefs.edit();
            editor.putLong("ultima_atualizacao", 0);
            editor.commit();
            super.onCancelled();
        }

        @Override
        protected Void doInBackground(Void... params) {
            FeedApplication app = (FeedApplication) getApplication();
            RssDownloadHelper2.updateRssData(app.getRssUrl(), getContentResolver());
            RssDownloadHelper3.updateRssData(app.getRssUrl3(), getContentResolver());
            RssDownloadHelper4.updateRssData(app.getRssUrl4(), getContentResolver());

            return null;
        }
    }
    public void carregarNoticias(){
        SharedPreferences prefs = getPreferences(MODE_PRIVATE);

        long ultima = prefs.getLong("ultima_atualizacao", 0);
        if((System.currentTimeMillis() - ultima) > FREQUENCIA_ATUALIZACAO){
            tarefa = new AtualizarPostAsyncTask();
            tarefa.execute();
        }
    }

    @Override
    protected void onStop() {
        if(tarefa != null && !tarefa.getStatus().equals(AsyncTask.Status.FINISHED)){
            tarefa.cancel(true);
        }
        super.onStop();
    }
    public void setBarraProgressoVisivel(boolean visible){
        final Window window = getWindow();
        if(visible){
            window.setFeatureInt(Window.FEATURE_PROGRESS, Window.PROGRESS_VISIBILITY_ON);
            window.setFeatureInt(Window.FEATURE_PROGRESS, Window.PROGRESS_INDETERMINATE_ON);
        }else{
            window.setFeatureInt(Window.FEATURE_PROGRESS, Window.PROGRESS_VISIBILITY_OFF);
        }
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long idNoticia) {
        String link;
        try {
            link = "vazio";
            final String[] colunas = new String[]{
                    FeedsDB.Posts._ID, FeedsDB.Posts.TITLE, FeedsDB.Posts.PUB_DATE, FeedsDB.Posts.LINK
            };
            if(codigoFeed == 2){
                Uri uri = FeedsProvider2.CONTENT_URI;
                uri = ContentUris.withAppendedId(uri, idNoticia);
                cursor = managedQuery(uri, colunas, null, null, FeedsDB.Posts.PUB_DATE + " DESC");
                cursor.setNotificationUri(getContentResolver(), uri);
                startManagingCursor(cursor);
                int id = (int) idNoticia;
                cursor.moveToPosition(id-1);
                link = cursor.getString(cursor.getColumnIndex(FeedsDB.Posts.LINK));
                Log.i("clique", link);
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(link));
                //startActivity(Intent.createChooser(browserIntent, "Concluir ação com"));
                startActivity(browserIntent);
            }else if (codigoFeed == 3){
                Uri uri = FeedsProvider3.CONTENT_URI;
                uri = ContentUris.withAppendedId(uri, idNoticia);
                cursor = managedQuery(uri, colunas, null, null, FeedsDB.Posts.PUB_DATE + " DESC");
                cursor.setNotificationUri(getContentResolver(), uri);
                startManagingCursor(cursor);
                int id = (int) idNoticia;
                cursor.moveToPosition(id-1);
                link = cursor.getString(cursor.getColumnIndex(FeedsDB.Posts.LINK));
                Log.i("clique", link);
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(link));
                startActivity(browserIntent);
            }else if (codigoFeed == 4){
                Uri uri = FeedsProvider4.CONTENT_URI;
                uri = ContentUris.withAppendedId(uri, idNoticia);
                cursor = managedQuery(uri, colunas, null, null, FeedsDB.Posts.PUB_DATE + " DESC");
                cursor.setNotificationUri(getContentResolver(), uri);
                startManagingCursor(cursor);
                int id = (int) idNoticia;
                cursor.moveToPosition(id-1);
                link = cursor.getString(cursor.getColumnIndex(FeedsDB.Posts.LINK));
                Log.i("clique", link);
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(link));
                startActivity(browserIntent);
            }

        }catch (Exception e){
            e.printStackTrace();
            Log.i("clique", "falhou clique");
        }

    }
}
