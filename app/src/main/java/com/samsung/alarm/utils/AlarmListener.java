package com.samsung.alarm.utils;

import android.view.View;

import com.samsung.alarm.model.Alarm;

public interface AlarmListener {
    void onToggle(Alarm alarm);
    void onDelete(Alarm alarm);
    void onItemClick(Alarm alarm, View view);
}
