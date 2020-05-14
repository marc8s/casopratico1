package com.example.pratica23;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final TextView txt = findViewById(R.id.textView);
        Button botao = findViewById(R.id.button);
        botao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dados d = new Dados();
                try{
                    String dados = d.execute().get();
                    txt.setText(mostrar(dados));
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
    }

    private String mostrar(String dados){
        String[]info = dados.split("/");
        String temperatura=info[0];
        String humidade=info[1];
        String nascerSol=info[2];
        String porSol=info[3];
        double graus = Double.parseDouble(temperatura)-273.15;

        temperatura = "Temperatura: "+ String.format("%.1f", graus) + "graus";
        humidade = "Humidade: "+humidade;
        nascerSol="Nascer do Sol:" +getTime(nascerSol);
        porSol="Por do Sol:" + getTime(porSol);


        //return graus+ "\n"+humidade+"\n"+getTime(nascerSol)+"\n"+getTime(porSol);
        return temperatura + "\n" + humidade + "\n" + nascerSol+ "\n"+ porSol;
    }

    private String getTime(String time){
        Long tempo= Long.parseLong(time);
        DateFormat objFormatter = new SimpleDateFormat("HH:mm:ss");
        TimeZone tz = TimeZone.getDefault();
        objFormatter.setTimeZone(TimeZone.getTimeZone("GMT+01:00"));
        Calendar objCalendar = Calendar.getInstance(TimeZone.getTimeZone("GMT+01:00"));
        objCalendar.setTimeInMillis(tempo*1000);
        String result = objFormatter.format(objCalendar.getTime());
        objCalendar.clear();
        return result;
    }
}
