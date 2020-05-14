package com.example.casopratico1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import java.util.ArrayList;
import java.util.List;

public class SMSContatos extends ListActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_smscontatos);
        lerContatos();
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        Cursor tlfCur = getContentResolver().query(
                ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                null,
                ContactsContract.CommonDataKinds.Phone.CONTACT_ID+ " = ?",
                new String[]{"" + id}, null
        );

        int ntelefones = tlfCur.getCount();
        final String[] telefones = new String[ntelefones];
        int x= 0;
        while (tlfCur.moveToNext()){
            int col = tlfCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
            telefones[x++] = tlfCur.getString(col);
        }
        tlfCur.close();

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.selecionanumero);
        builder.setItems(telefones, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                SharedPreferences preferences = getSharedPreferences("info", 0);
                SharedPreferences.Editor editor = preferences.edit();
                String numerotelefone = telefones[i];
                editor.putString("numero", numerotelefone);
                editor.commit();

                Intent intent = new Intent();
                intent.setClass(getApplicationContext(), SMSEnviar.class);
                startActivity(intent);
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }

    public void lerContatos(){
        Cursor cursor;
        String[] campos = new String[]{ContactsContract.Contacts.DISPLAY_NAME};
        int[] views = new int[]{android.R.id.text1};
        String condicao = ContactsContract.Contacts.HAS_PHONE_NUMBER + " ='1'";

        cursor = getContentResolver().query(ContactsContract.Contacts.CONTENT_URI,
                null, condicao, null, null);
        SimpleCursorAdapter adapter = new SimpleCursorAdapter(this,
                android.R.layout.simple_list_item_1,
                cursor, campos, views, 0);
        setListAdapter(adapter);


    }
}
