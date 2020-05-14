package com.example.pratica21;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.database.Cursor;
import android.widget.RemoteViews;

/**
 * Implementation of App Widget functionality.
 */
public class Widget extends AppWidgetProvider {

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        final String[] colunas =new String[]{
                FeedsDB.Posts._ID,
                FeedsDB.Posts.TITLE,
                FeedsDB.Posts.PUB_DATE,
                FeedsDB.Posts.DESCRIPTION

        }  ;
        Cursor c = context.getContentResolver().query(FeedsProvider.CONTENT_URI, colunas,
                null, null, FeedsDB.Posts.PUB_DATE+ " DESC");
       


        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget);

        if(c.moveToFirst()){
               views.setTextViewText(R.id.texto, c.getString(1));
        }


        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
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

