package com.example.casopratico2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.IOException;

public class IniciarGravacao extends AppCompatActivity {
    private MediaRecorder gravar = null;
    private static final String LOG_TAG = "AudioRecordTest";
    private static String fileName = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_iniciar_gravacao);
        fileName = getExternalCacheDir().getAbsolutePath();
        fileName += "/audiorecordtest.3gp";

        startRecording();
        Button btnfecharGravacao = (Button) findViewById(R.id.button);

        btnfecharGravacao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stopRecording();
                finish();
            }
        });

    }

    private void startRecording(){

        gravar = new MediaRecorder();
        gravar.setAudioSource(MediaRecorder.AudioSource.MIC);
        gravar.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        gravar.setOutputFile(fileName);
        gravar.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
        try {
            gravar.prepare();
        }catch (IOException e){
            Log.e(LOG_TAG, "prepare() failed()");
        }
        gravar.start();

    }

    @Override
    protected void onStop() {
        super.onStop();
        if(gravar != null){
            gravar.release();
            gravar = null;
        }
    }

    private void stopRecording(){
        gravar.stop();
        gravar.release();
        gravar = null;
        Intent intent = new Intent(this, ReproduzirGravacao.class);
        startActivity(intent);
    }
}
