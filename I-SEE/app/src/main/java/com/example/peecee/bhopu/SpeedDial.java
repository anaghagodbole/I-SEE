package com.example.peecee.bhopu;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.v4.app.ActivityCompat;
import android.widget.Button;
import android.widget.RemoteViews;

/**
 * Implementation of App Widget functionality.
 */
public class SpeedDial extends AppWidgetProvider {
    Button con1, con2, con3, con4;


    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {


        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.speed_dial);

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        ComponentName componentName = new ComponentName(context, SpeedDial.class);
        int[] allWidgetId = appWidgetManager.getAppWidgetIds(componentName);
        for (int widgetId : allWidgetId) {
            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.speed_dial);
            views.setOnClickPendingIntent(R.id.con1, getPendingSelfIntent(context, "contact1"));
            views.setOnClickPendingIntent(R.id.con2, getPendingSelfIntent(context, "contact2"));
            views.setOnClickPendingIntent(R.id.con3, getPendingSelfIntent(context, "contact3"));
            views.setOnClickPendingIntent(R.id.con4, getPendingSelfIntent(context, "contact4"));
            appWidgetManager.updateAppWidget(widgetId, views);
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

    protected PendingIntent getPendingSelfIntent(Context context, String action) {
        Intent intent = new Intent(context, getClass());
        intent.setAction(action);
        return PendingIntent.getBroadcast(context, 0, intent, 0);

    }

    @Override
    public void onReceive(Context context, Intent intent) {


        super.onReceive(context, intent);
        if (ActivityCompat.checkSelfPermission(context, android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        String pnum="";
        Intent call = null;
        try{
           call = new Intent(Intent.ACTION_CALL);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        call.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        SharedPreferences s = PreferenceManager.getDefaultSharedPreferences(context);

        switch(intent.getAction()) {

            case "contact1":pnum=s.getString("con1","def");

                            break;
            case "contact2":pnum=s.getString("con2","def");
                            break;
            case "contact3":pnum=s.getString("con3","def");
                            break;
            case "contact4":pnum=s.getString("con4","def");
                            break;



    }
        call.setData(Uri.parse("tel:"+pnum));
        context.startActivity(call);
    }

}

