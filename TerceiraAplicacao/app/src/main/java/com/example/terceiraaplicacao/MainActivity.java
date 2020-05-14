package com.example.terceiraaplicacao;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //associacao da classe com o layout
        setContentView(R.layout.activity_main);

        //associacao do objeto botao1 a variavel bt1
        Button bt1 = (Button) findViewById(R.id.botao1);
        //metodo que responde ao clique do botao
        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //inicia nova atividade - atividade 2
                Intent intent = new Intent();
                intent.setClass(getApplicationContext(), Atividade2.class);
                //envio do parametro chamado parametro1
                intent.putExtra("parametro1", "este é um parametro");
                startActivity(intent);
            }
        });

        //associacao do objeto botao2 a variavel bt2
        Button bt2 = (Button) findViewById(R.id.botao2);
        bt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(getApplicationContext(), Atividade3.class);
                //envio do parametro chamado parametro1
                intent.putExtra("parametro1", "este é um parametro");
                startActivity(intent);
            }
        });
    }
}
