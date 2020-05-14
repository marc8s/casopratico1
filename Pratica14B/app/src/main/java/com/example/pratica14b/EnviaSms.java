package com.example.pratica14b;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class EnviaSms extends AppCompatActivity {
    EditText mensagem;
    String numeropassado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_envia_sms);
        SharedPreferences dados = getSharedPreferences("info", 0);
        numeropassado = dados.getString("numero", "");
        TextView numero = (TextView) findViewById(R.id.textView);
        mensagem = (EditText) findViewById(R.id.editText);
        Button envio = (Button) findViewById(R.id.button);
        numero.setText(numeropassado);

        envio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!mensagem.getText().toString().isEmpty())
                    enviarSms();
                else
                    Toast.makeText(getApplicationContext(), "Por favor escreve o textinho ai", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == 200 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
            enviarSms();
        }else{
            Toast.makeText(this, "Não tem permissao para ver enviar mensagem", Toast.LENGTH_SHORT).show();
        }
    }

    public void enviarSms(){
        if(checkSelfPermission(Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED){
            requestPermissions(new String[]{Manifest.permission.SEND_SMS}, 200);
        }else{
            String texto = mensagem.getText().toString();

            android.telephony.SmsManager sms = android.telephony.SmsManager.getDefault();
            sms.sendTextMessage(numeropassado,null, texto,null,null);
            finish();
        }
    }
}
