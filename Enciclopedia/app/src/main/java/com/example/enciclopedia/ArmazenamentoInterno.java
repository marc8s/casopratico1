package com.example.enciclopedia;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;

public class ArmazenamentoInterno extends AppCompatActivity {

    private Button armazenar;
    private Button recuperar;
    private Button voltar;
    private TextView texto;
    private String nome_ficheiro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.armazenamento_interno);

        nome_ficheiro = "dados.txt";

        armazenar = (Button) findViewById(R.id.btn_armazenar);
        recuperar = (Button) findViewById(R.id.btn_recuperar);
        voltar = (Button) findViewById(R.id.btn_voltar);
        texto = (TextView) findViewById(R.id.texto);

        armazenar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                armazenarDados();
            }
        });

        recuperar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recuperarDados();
            }
        });

        voltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void armazenarDados(){
        FileOutputStream f1;

        try{
            f1 = openFileOutput(nome_ficheiro, Context.MODE_PRIVATE);
            String str = "Shantay you stay";
            f1.write(str.getBytes());
            f1.close();

        }catch(Exception ex){
            texto.setText("Sorry my dear, ERRO AO ESCREVER");
        }
    }

    private void recuperarDados(){
        FileInputStream fin;

        try{
            /*fin = openFileInput(nome_ficheiro);
            int c;
            String dados = "";
            while(c = fin.read() != -1){
                dados = dados + (char)c;
            }
            fin.close();
            texto.setText(dados);*/
            InputStream is = openFileInput(nome_ficheiro);
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String text = "";
            StringBuilder sb = new StringBuilder();
            while((text = br.readLine())!= null){
                sb.append(text);
            }
            is.close();
            texto.setText(sb.toString());

        }catch(Exception ex){
            texto.setText("Sorry my dear, ERRO AO ABRIR");
        }
    }
}
