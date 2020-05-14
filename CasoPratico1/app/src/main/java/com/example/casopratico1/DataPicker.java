package com.example.casopratico1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;

import java.util.Calendar;


public class DataPicker extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_picker);
        final DatePicker dataAniversario = (DatePicker) findViewById(R.id.datePicker);


        Button btn = (Button) findViewById(R.id.button2);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int year=dataAniversario.getYear();
                int month=dataAniversario.getMonth();
                int day=dataAniversario.getDayOfMonth();
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR);
                int mMonth = c.get(Calendar.MONTH);
                int anoLocal, idade, dias;

                final Calendar aniversario = Calendar.getInstance();

                idade = mYear - year;

                if(mMonth > month){
                    anoLocal=mYear+1;
                    idade = idade+1;

                }else{
                    anoLocal = year + idade;
                }
                aniversario.set(anoLocal, month, day);
                long milis1 = c.getTimeInMillis();
                long milis2 = aniversario.getTimeInMillis();
                long diff = milis2 - milis1;

                dias = (int)(diff/(24*60*60*1000));


                final Context context = DataPicker.this;

                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putInt("idade", idade);
                editor.putInt("dias", dias);
                editor.commit();
                finish();
            }
        });
    }
}
