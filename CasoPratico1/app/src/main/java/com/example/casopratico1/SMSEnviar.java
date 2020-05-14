package com.example.casopratico1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class SMSEnviar extends AppCompatActivity {
    EditText mensagem;
    String numeropassado;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_smsenviar);
        SharedPreferences preferences = getSharedPreferences("info", 0);
        numeropassado = preferences.getString("numero", "");
        TextView numero = (TextView) findViewById(R.id.textView12);
        mensagem = (EditText) findViewById(R.id.editText2);
        Button envio = (Button) findViewById(R.id.button3);
        numero.setText(numeropassado);

        envio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!mensagem.getText().toString().isEmpty()){
                    enviarSMS();
                }else{
                    Toast.makeText(getApplicationContext(), R.string.mensagemvazia, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void enviarSMS(){
        String texto = mensagem.getText().toString();
        android.telephony.SmsManager sms = android.telephony.SmsManager.getDefault();
        sms.sendTextMessage(numeropassado, null, texto, null, null);
        Toast.makeText(getApplicationContext(), R.string.smsenviada , Toast.LENGTH_LONG).show();
        finish();
    }
}
