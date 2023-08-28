package com.samsung.alarm.services;

import android.app.Notification;
import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

import com.samsung.alarm.R;
import com.samsung.alarm.notification.NotificationHelper;

import java.net.URI;

public class Music extends Service {
    MediaPlayer mediaPlayer;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String state = intent.getExtras().getString("state");
        int curState = 2;
        if(state.equals("on")){
//        startForeground();
            curState = 1;
        }else if(state.equals("off")){
            curState = 0;
        }
        if(curState == 1){
            mediaPlayer = MediaPlayer.create(this, R.raw.music);
            mediaPlayer.start();
            mediaPlayer.setLooping(true);
            Log.d("Music", "Start Music hehe");
        }else if(curState == 0 && mediaPlayer != null){
            mediaPlayer.stop();
            mediaPlayer.reset();
        }
        super.onStartCommand(intent, flags, startId);
//        NotificationHelper notificationHelper = new NotificationHelper(this);
//        startForeground(1, notificationHelper.getChannelNotification().build());
        // Lam gi do
        return START_NOT_STICKY;
    }

    @Nullable
    @Override
    public ComponentName startForegroundService(Intent service) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            this.startForegroundService(new Intent(this, Music.class));
        } else {
            this.startService(new Intent(this, Music.class));
        }

        return super.startForegroundService(service);
    }

    @Override
    public void onDestroy() {
//        super.onDestroy();
        mediaPlayer.stop();
        Log.d("Music", "Destroy");
    }
}
