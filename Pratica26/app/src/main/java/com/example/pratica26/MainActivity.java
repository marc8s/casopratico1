package com.example.pratica26;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    private static int recurso;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(savedInstanceState == null){
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new Imagem())
                    .commit();
        }
        Button btcao = findViewById(R.id.button1);
        Button btgato = findViewById(R.id.button2);
        Button bthamster = findViewById(R.id.button3);
        Button btcoelho = findViewById(R.id.button4);
        Button btporquinho = findViewById(R.id.button5);

        btcao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                muda(R.drawable.cao, R.raw.cao);
            }
        });
        btgato.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                muda(R.drawable.gato, R.raw.gato);
            }
        });
        bthamster.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                muda(R.drawable.cavalo, R.raw.cavalo);
            }
        });
        btcoelho.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                muda(R.drawable.ovelha, R.raw.ovelha);
            }
        });
        btporquinho.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                muda(R.drawable.porco, R.raw.porco);
            }
        });
    }





    private void muda(int r, int som){
        recurso = r;
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, new Imagem()).commit();

        MediaPlayer mp = MediaPlayer.create(getApplicationContext(), som);
        mp.start();
    }


    public static class Imagem extends Fragment{
        @Nullable
        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragmento1, container, false);
            ImageView img = rootView.findViewById(R.id.imageView1);
            img.setImageResource(MainActivity.recurso);
            return rootView;
        }
    }
}
