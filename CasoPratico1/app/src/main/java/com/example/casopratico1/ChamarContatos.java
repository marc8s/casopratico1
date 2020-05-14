package com.example.casopratico1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

public class ChamarContatos extends ListActivity {
    private Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chamar_contatos);
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
                Intent chamada = new Intent(Intent.ACTION_CALL);
                chamada.setData(Uri.parse("tel:"+telefones[i]));
                startActivity(chamada);
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }


    public void lerContatos(){
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
