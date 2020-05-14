package com.example.terceiraaplicacao;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;

public class Atividade2 extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ecra_nova_atividade);

        //associacao da variavel texto a textoView presente no layout da atividade 2
        TextView texto = (TextView) findViewById(R.id.textView);
        //recolha do parametro enviado pela atividade main
        Intent intent = getIntent();
        Bundle parametrorecebido = intent.getExtras();
        //passagem do texto recebido como parametro
        texto.setText(parametrorecebido.getString("parametro1"));

        Button bt1 = (Button) findViewById(R.id.botao1);
        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //encerra atividade
                finish();
            }
        });
    }
}
