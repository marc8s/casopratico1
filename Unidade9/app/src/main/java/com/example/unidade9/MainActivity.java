package com.example.unidade9;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    final private int REQUEST_MULTIPLE_PERMISSIONS = 124;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AccessSms();

        final EditText n_telefone = (EditText)findViewById(R.id.editTextTelefone);
        final EditText mensagem = (EditText)findViewById(R.id.editTextMensagem);
        Button botao = (Button)findViewById(R.id.buttonEnviar);

        botao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String destino = n_telefone.getText().toString();
                String texto = mensagem.getText().toString();
                enviaSMS(destino, texto);
                n_telefone.setText("");
                mensagem.setText("");
            }
        });
    }

    private void enviaSMS(String destino, String texto){

        try{
            //envio mensagem
            SmsManager smsMgr = SmsManager.getDefault();
            smsMgr.sendTextMessage(destino, null, texto, null, null);
            //se tivr acesso ao fornecedor de conteudos sms, guarda a mensagem na pasta enviada
            if(getPackageManager().resolveContentProvider("sms", 0) != null){
                ContentValues values = new ContentValues();
                values.put("address", destino);
                values.put("body", texto);
                Uri uri = Uri.parse("content://sms/sent");
                getContentResolver().insert(uri,values);
            }
            //alerta envio
            Toast.makeText(this, "SMS enviado", Toast.LENGTH_SHORT).show();
        }catch(Exception e){
            Toast.makeText(this, "Erro no envio: "+ e.getLocalizedMessage(),
                    Toast.LENGTH_LONG).show();
        }
    }

    private void AccessSms(){
        List<String> permissionsNeeded = new ArrayList<String>();
        final List<String> permissionsList = new ArrayList<>();
        if (!addPermission(permissionsList, Manifest.permission.SEND_SMS))
            permissionsNeeded.add("Send Sms");

        if(permissionsList.size() > 0){
            if(permissionsNeeded.size()>0){
                String message = "You need to grant access to "+ permissionsNeeded.get(0);
                for (int i = 0; i < permissionsNeeded.size(); i++)
                    message = message + ", "+permissionsNeeded.get(i);
                showMessageOkCancel(message, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        requestPermissions(permissionsList.toArray(new String[permissionsList.size()]),
                                REQUEST_MULTIPLE_PERMISSIONS);
                    }
                });
                return;
            }
            requestPermissions(permissionsList.toArray(new String[permissionsList.size()]),
                    REQUEST_MULTIPLE_PERMISSIONS);
            return;
        }
    }

    private boolean addPermission(List<String> permissionsList, String permission){
        if (checkSelfPermission(permission) != PackageManager.PERMISSION_GRANTED){
            permissionsList.add(permission);
            if (!shouldShowRequestPermissionRationale(permission))
                return false;
        }
        return true;
    }

    private void showMessageOkCancel(String message, DialogInterface.OnClickListener okListener){
        new AlertDialog.Builder(MainActivity.this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }
}
