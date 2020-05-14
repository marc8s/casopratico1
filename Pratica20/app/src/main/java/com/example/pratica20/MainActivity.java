package com.example.pratica20;

import androidx.appcompat.app.AppCompatActivity;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;

public class MainActivity extends AppCompatActivity {

    private int mAppWidgetId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final DatePicker datautilizador = (DatePicker) findViewById(R.id.datePicker);
        datautilizador.init(2020, 6, 1, null);
        //obter id do widget
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (extras != null){
            mAppWidgetId = extras.getInt(AppWidgetManager.EXTRA_APPWIDGET_ID,
                    AppWidgetManager.INVALID_APPWIDGET_ID);
        }
        //fecha atividade se n obter id
        if (mAppWidgetId == AppWidgetManager.INVALID_APPWIDGET_ID){
            finish();
        }

        Button bt1=(Button)findViewById(R.id.button);
        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int year=datautilizador.getYear();
                int month=datautilizador.getMonth();
                int day=datautilizador.getDayOfMonth();

                final Context context = MainActivity.this;
                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putInt("ano", year);
                editor.putInt("mes", month);
                editor.putInt("dia", day);
                editor.commit();
                AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);

                //atualizar widget
                MyWidget.updateAppWidget(context, appWidgetManager, mAppWidgetId);
                Intent resultValue = new Intent();
                resultValue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, mAppWidgetId);
                setResult(RESULT_OK, resultValue);
                finish();
            }
        });
    }
}
