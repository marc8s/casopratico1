package com.example.pratica13;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    String uri;
    static final int PHONECALL = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final EditText numero = findViewById(R.id.numero);
        Button botao1 = findViewById(R.id.button);
        Button botao2 = findViewById(R.id.button2);

        botao1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uri = numero.getText().toString();
                String[] PERMISSIONS = {Manifest.permission.CALL_PHONE};
                if(!hasPermissions(getApplicationContext(), PERMISSIONS)){
                    ActivityCompat.requestPermissions(MainActivity.this, PERMISSIONS, PHONECALL);
                }else {
                    chamar(uri);
                }
            }
        });
        botao2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uri = numero.getText().toString();
                String[] PERMISSIONS = {Manifest.permission.CALL_PHONE};
                if(!hasPermissions(getApplicationContext(), PERMISSIONS)){
                    ActivityCompat.requestPermissions(MainActivity.this, PERMISSIONS, PHONECALL);
                }else {
                    chamar2(uri);
                }
            }
        });

    }

    private void chamar(String uri){
        Intent call = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + uri));
        startActivity(call);
    }
    private void chamar2(String uri){
        Intent call = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + uri));
        startActivity(call);
    }
    private static boolean hasPermissions(Context context, String[] permissions){
        if(permissions != null){
            for (String permission : permissions){
                if(ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED){
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case PHONECALL:{
                if(grantResults.length> 0 &&grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    chamar(uri);
                }else{
                    Toast.makeText(getApplicationContext(), "Não tem permissão.", Toast.LENGTH_LONG).show();
                }
            }
        }
    }
}
