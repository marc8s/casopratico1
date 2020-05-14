package com.example.pratica16;

import androidx.appcompat.app.AppCompatActivity;

import android.gesture.Gesture;
import android.gesture.GestureLibraries;
import android.gesture.GestureLibrary;
import android.gesture.GestureOverlayView;
import android.gesture.Prediction;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements GestureOverlayView.OnGesturePerformedListener {

    GestureOverlayView gestureView;
    TextView nomeGesto;
    GestureLibrary gestLib;

    @Override
    protected void onResume() {
        super.onResume();
        gestureView.addOnGesturePerformedListener(this);
    }

    @Override
    protected void onStop() {
        gestureView.removeAllOnGestureListeners();
        super.onStop();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gestureView = findViewById(R.id.gesture);
        nomeGesto= findViewById(R.id.textView);

        gestLib= GestureLibraries.fromRawResource(this, R.raw.gesture);
        if(!gestLib.load()){
            Toast.makeText(this, "erro ao carrgera biblioteca", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onGesturePerformed(GestureOverlayView overlay, Gesture gesture) {
        ArrayList<Prediction> previsoes = gestLib.recognize(gesture);
        if(previsoes.size()>0){
            Prediction previsao= previsoes.get(0);

            if(previsao.score>1.0){
                nomeGesto.append("| "+previsao.name);
                return;
            }else{
                nomeGesto.append("");
            }
        }else{
            nomeGesto.setText(" gesto desconhecido ");
        }
    }
}
