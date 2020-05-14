package com.example.pratica20;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.widget.RemoteViews;

import java.util.Calendar;

/**
 * Implementation of App Widget functionality.
 */
public class MyWidget extends AppWidgetProvider {
    private static int dias, ano, mes, dia;

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        calculos(context);
        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.my_widget);
        views.setTextColor(R.id.textView2, Color.parseColor("#FF0000"));
        views.setTextViewText(R.id.textView2, dias + " dias para ");
        views.setTextColor(R.id.textView3, Color.parseColor("#0000FF"));
        views.setTextViewText(R.id.textView3, dia + "/" + mes + "/" + ano);

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }
    protected static int dias(){
        //obtem data atual
        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        //data até o fim de dias
        Calendar fimdeano = Calendar.getInstance();
        // ano seguinte
        fimdeano.set(mYear+1, 0, 1);
        //calculo da diferença em dias entre as duas datas
        //é preciso fazer o alculo em milisegundos
        long milis1 = c.getTimeInMillis();
        long milis2 = fimdeano.getTimeInMillis();

        long diff = milis2 - milis1;

        int dias = (int)(diff/(24*60*60*1000));
        return dias;
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }
    private static void calculos(Context context){
        //obtem data atual
        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);

        SharedPreferences datautilizador = PreferenceManager.getDefaultSharedPreferences(context);
        mes = datautilizador.getInt("mes", 0)+1;
        dia = datautilizador.getInt("dia", 0);

        final Calendar aniversario = Calendar.getInstance();
        if(mMonth > mes-1){
            ano=mYear+1;
        }
        if(mMonth == mes-1 && mDay > dia){
            ano=mYear+1;
        }
        aniversario.set(ano, mes-1, dia);
        long milis1 = c.getTimeInMillis();
        long milis2 = aniversario.getTimeInMillis();
        long diff = milis2 - milis1;

        dias = (int)(diff/(24*60*60*1000));
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

