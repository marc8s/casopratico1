package com.example.enciclopedia;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class PreferenciasCor extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preferencias_cor);

        SharedPreferences dadosguardados=getPreferences(0);
        int n_vezes=dadosguardados.getInt("numVezes", 0);
        Toast.makeText(this, "Aplicação aberta " + n_vezes + " vezes", Toast.LENGTH_LONG).show();

        Button bt1 = (Button) findViewById(R.id.btn_mudarcor);
        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent();
                i.setClass(getApplicationContext(), PreferenciasActivity.class);
                startActivity(i);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        try{
            TextView texto = (TextView)findViewById(R.id.textview_prefcor);
            PreferenceManager.setDefaultValues(this, R.xml.preferences, false);

            SharedPreferences pref=PreferenceManager.getDefaultSharedPreferences(this);
            String corNova=pref.getString("pref_color", "#999999");
            int color= Color.parseColor(corNova);

            texto.setTextColor(color);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
