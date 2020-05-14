package com.example.casopratico1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    private static int dias, idade;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button btInfoDeveloper = (Button) findViewById(R.id.button12);
        Button btDataBaseCitys = (Button) findViewById(R.id.button7);
        Button btMapsActivity = (Button) findViewById(R.id.button8);
        Button btDataPicker = (Button) findViewById(R.id.button9);
        Button btChamarContatos = (Button) findViewById(R.id.button10);
        Button btSMSContatos = (Button) findViewById(R.id.button11);
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_CONTACTS}, 200);
        }

        btInfoDeveloper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(getApplicationContext(), InfoDeveloper.class);
                startActivity(intent);
            }
        });

        btDataBaseCitys.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(getApplicationContext(), DataBaseCitys.class);
                startActivity(intent);
            }
        });

        btMapsActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(getApplicationContext(), MapsActivity.class);
                startActivity(intent);
            }
        });

        btDataPicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(getApplicationContext(), DataPicker.class);
                startActivity(intent);
            }
        });

        btChamarContatos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(getApplicationContext(), ChamarContatos.class);
                startActivity(intent);
            }
        });

        btSMSContatos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(getApplicationContext(), SMSContatos.class);
                startActivity(intent);
            }
        });


    }



    @Override
    protected void onResume() {

        carregarPreferencias();
        try{

            TextView txtDia = (TextView) findViewById(R.id.textView7);
            TextView txtIdade = (TextView) findViewById(R.id.textView9);
            String diaTexto = String.valueOf(dias);
            String idadeTexto = String.valueOf(idade) +"º";
            txtDia.setText(diaTexto);
            txtIdade.setText(idadeTexto);

        }catch (Exception e){
            e.printStackTrace();
        }
        super.onResume();
    }

    public void carregarPreferencias(){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        dias = preferences.getInt("dias", 0);
        idade = preferences.getInt("idade", 0);
    }


}
