package com.example.pratica21;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ListActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.text.format.DateFormat;
import android.widget.SimpleCursorAdapter.ViewBinder;



public class MainActivity extends ListActivity {
    private static final long FREQUENCIA_ATUALIZACAO = 60*60*1000;
    private AtualizarPostAsyncTask tarefa;
    Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_PROGRESS);
        setContentView(R.layout.activity_main);
        setTitle(R.string.titulo_noticias);
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
        FeedsDB.Posts.FRASE,
        FeedsDB.Posts.PUB_DATE,};
        Uri uri = Uri.parse("content://blog.masterd.pt/post");
        cursor = managedQuery(uri, colunas, null, null, FeedsDB.Posts.PUB_DATE + " DESC");
        cursor.setNotificationUri(getContentResolver(), uri);
        startManagingCursor(cursor);

        String[] camposDb = new String[]{
                FeedsDB.Posts.TITLE, FeedsDB.Posts.FRASE, FeedsDB.Posts.PUB_DATE
        };
        int[] camposView = new int[]{
                R.id.feedTitulo, R.id.feedFrase, R.id.feedData
        };

        SimpleCursorAdapter adapter = new SimpleCursorAdapter(this, R.layout.feeds_item, cursor, camposDb, camposView, 0);
        final java.text.DateFormat dateFormat = DateFormat.getLongDateFormat(MainActivity.this);
        adapter.setViewBinder(new SimpleCursorAdapter.ViewBinder() {
            @Override
            public boolean setViewValue(View view, Cursor cursor, int columnIndex) {
                if(view.getId() == R.id.feedData){
                    long timestamp = cursor.getLong(columnIndex);
                    ((TextView)view).setText(dateFormat.format(timestamp));
                    return true;
                }else{
                    return false;
                }
            }
        });
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
            Editor editor = prefs.edit();
            editor.putLong("ultima_atualizacao", System.currentTimeMillis());
            editor.commit();
            setBarraProgressoVisivel(false);
            //finish();
        }

        @Override
        protected void onCancelled() {
            setBarraProgressoVisivel(false);
            SharedPreferences prefs = getPreferences(MODE_PRIVATE);
            Editor editor = prefs.edit();
            editor.putLong("ultima_atualizacao", 0);
            editor.commit();
            super.onCancelled();
        }

        @Override
        protected Void doInBackground(Void... params) {

            MasterdApplication app = (MasterdApplication) getApplication();
            RssDownloadHelper.updateRssData(app.getRssUrl(), getContentResolver());
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
    protected void onListItemClick(ListView l, View v, int position, long id) {
        Intent i = new Intent();
        i.setClass(MainActivity.this, NoticiaActivity.class);
        i.putExtra("idNoticia", id);
        startActivity(i);
    }
}
