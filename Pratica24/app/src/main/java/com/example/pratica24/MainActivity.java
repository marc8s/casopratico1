package com.example.pratica24;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    static final String TAG = "Resultado";
    EditText txtCelsius;
    Button botao;
    String celsius, resultado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtCelsius = findViewById(R.id.editText);
        botao = findViewById(R.id.button);
        botao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                celsius = txtCelsius.getText().toString();
                TarefaAsync ta = new TarefaAsync();
                ta.execute();
            }
        });
        /*botao.setOnClickListener((v)->{
            celsius = txtCelsius.getText().toString();
            TarefaAsync ta = new TarefaAsync();
            ta.execute();
        });*/
    }
    private class TarefaAsync extends AsyncTask<Void, Void, Void>{
        @Override
        protected void onPostExecute(Void aVoid) {
            Log.i(TAG,"onPostExecute");
            Toast.makeText(MainActivity.this, resultado+" Fahrenheit", Toast.LENGTH_LONG).show();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            Log.i(TAG, "onPostExecute");
            resultado = SOAPClass.calculoRemoto(celsius);
            return null;
        }

        @Override
        protected void onPreExecute() {
            Log.i(TAG, "onPreExecute");
        }
    }
}
