package com.example.pratica17;

import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.io.IOException;

import static android.Manifest.permission.RECORD_AUDIO;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class MainActivity extends AppCompatActivity {
    private ToggleButton botaoGravar;
    private MediaRecorder gravador;
    private ToggleButton botaoReproduzir;

    private MediaPlayer reprodutor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        botaoReproduzir = findViewById(R.id.toggleButton2);
        botaoReproduzir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(botaoReproduzir.isChecked()){
                    reproduzir();
                }else{
                    parar();
                }
            }
        });


        //inicialização da variavel gravador
        gravador = new MediaRecorder();

        botaoGravar = (ToggleButton) findViewById(R.id.toggleButton);
        if (checkPermission()){
            botaoGravar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(botaoGravar.isChecked()){
                        startRecording();
                    }else{
                        stopRecording();
                    }
                }
            });
        }else requestPermission();

    }

    private void reproduzir(){
        reprodutor = new MediaPlayer();
        if(reprodutor==null){
            Toast.makeText(MainActivity.this, "Erro ao carregar o MediaPlayer", Toast.LENGTH_LONG).show();
        }
        try {
            String filename = Environment.getExternalStorageDirectory().getAbsolutePath() + "/test.3gp";
            reprodutor.setDataSource(MainActivity.this, Uri.parse(filename));
            reprodutor.prepare();
            reprodutor.start();
        }catch(IOException e){
            e.printStackTrace();
        }
        botaoReproduzir.setChecked(true);
    }

    private void startRecording(){
        gravador = new MediaRecorder();
        gravador.setAudioSource(MediaRecorder.AudioSource.MIC);
        gravador.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        gravador.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
        String filename = Environment.getExternalStorageDirectory().getAbsolutePath() + "/test.3gp";
        gravador.setOutputFile(filename);
        try{
            gravador.prepare();
            gravador.start();
            botaoGravar.setChecked(true);
        }catch(Exception e){
            Log.d("start recording", e.toString());
            Toast.makeText(getBaseContext(), "Ocorreu um erro na gravação.", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    protected void parar(){
        reprodutor.stop();
        reprodutor.reset();
        botaoReproduzir.setChecked(false);
    }

    private void stopRecording(){
        botaoGravar.setChecked(false);
        gravador.stop();
        gravador.reset();
    }

    private void requestPermission(){
        ActivityCompat.requestPermissions(MainActivity.this,
                new String[]{WRITE_EXTERNAL_STORAGE, RECORD_AUDIO}, 100);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case 100:
                if (grantResults.length>0){
                    boolean StoragePermission = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean RecordPermission =  grantResults[1] == PackageManager.PERMISSION_GRANTED;

                    if(StoragePermission && RecordPermission){
                        Toast.makeText(MainActivity.this, "Permissão OK", Toast.LENGTH_LONG).show();
                    }else{
                        Toast.makeText(MainActivity.this, "Sem Permissão", Toast.LENGTH_LONG).show();
                    }
                }
                break;
        }
    }

    public boolean checkPermission(){
        int result = ContextCompat.checkSelfPermission(getApplicationContext(), WRITE_EXTERNAL_STORAGE);
        int result1 = ContextCompat.checkSelfPermission(getApplicationContext(), RECORD_AUDIO);
        return result == PackageManager.PERMISSION_GRANTED && result1 == PackageManager.PERMISSION_GRANTED;
    }
}
