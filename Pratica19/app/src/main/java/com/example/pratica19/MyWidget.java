package com.example.pratica19;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.graphics.Color;
import android.widget.RemoteViews;

import java.util.Calendar;

/**
 * Implementation of App Widget functionality.
 */
public class MyWidget extends AppWidgetProvider {

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {


        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.my_widget);
        views.setTextColor(R.id.textView2, Color.parseColor("#FF0000"));
        views.setTextViewText(R.id.textView2, dias() + " Dias");

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

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

