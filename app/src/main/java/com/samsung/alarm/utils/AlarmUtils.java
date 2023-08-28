package com.samsung.alarm.utils;

import static androidx.core.content.ContextCompat.getSystemService;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import com.samsung.alarm.notification.AlertReceiver;
import java.util.Calendar;

public class AlarmUtils {
    public static void startAlarm(Calendar c, Context ct, AlarmManager mAlarmManager) {
//        AlarmManager alarmManager = (AlarmManager) getSystemService(ct ,Context.ALARM_SERVICE);
        Intent intent = new Intent(ct, AlertReceiver.class);
        intent.putExtra("state", "on");
        PendingIntent pendingIntent = PendingIntent.getBroadcast(ct, 1, intent, PendingIntent.FLAG_IMMUTABLE);


        if (c.before(Calendar.getInstance())) {
            c.add(Calendar.DATE, 1);
        }


        mAlarmManager.setExact(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), pendingIntent);
    }

    public static void cancelAlarm(Context ct, AlarmManager mAlarmManager) {
//        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(ct, AlertReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(ct, 1, intent, PendingIntent.FLAG_IMMUTABLE);
        intent.putExtra("state", "off");
        ct.sendBroadcast(intent);
        mAlarmManager.cancel(pendingIntent);
//        mTextView.setText("Alarm canceled");
    }
}
