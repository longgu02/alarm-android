package com.samsung.alarm.notification;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Button;

import androidx.core.app.NotificationCompat;

import com.samsung.alarm.R;
import com.samsung.alarm.StopAlarmActivity;
import com.samsung.alarm.StopwatchActivity;
import com.samsung.alarm.services.Music;


public class AlertReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        // Service Music
        Intent stopIntent = new Intent(context, StopAlarmActivity.class);
        PendingIntent stopPendingIntent = PendingIntent.getActivity(context, 1, stopIntent, PendingIntent.FLAG_IMMUTABLE);
        String ex = intent.getExtras().getString("state");
        if(ex != null){
            if(ex.equals("on")){
                NotificationHelper notificationHelper = new NotificationHelper(context);
                NotificationCompat.Builder nb = notificationHelper.getChannelNotification();
                notificationHelper.getManager().notify(1, nb.setContentIntent(stopPendingIntent).build());
            }
            Intent myIntent = new Intent(context, Music.class);
            myIntent.putExtra("state", ex);
            context.startService(myIntent);
            Log.d("Alert Receiver", ex);
        }
        //ContextCompat.startForegroundService(context, myIntent);
        //context.startForegroundService(myIntent);


    }
}