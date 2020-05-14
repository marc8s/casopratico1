package com.example.unidade9;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ListActivity;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.widget.SimpleCursorAdapter;

public class SendSmsActivity extends ListActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_sms);

        //carrega-se lista de mensagens
        Uri uri = Uri.parse("content://sms/sent");
        Cursor cursor = getContentResolver().query(uri, null, null,
                null, null);
        startManagingCursor(cursor);

        //relaciona-se colunas com a vista
        String[] columns = new String[] {"address", "body"};
        int[] names = new int[]{android.R.id.text1, android.R.id.text2};
        //usa-se o adapter do sistema
        SimpleCursorAdapter adapter = new SimpleCursorAdapter(this, android.R.layout.simple_expandable_list_item_2,
                cursor, columns, names);
        setListAdapter(adapter);


    }
}
