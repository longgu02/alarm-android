package com.samsung.alarm.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.samsung.alarm.model.Alarm;

import java.util.ArrayList;
public class SqliteDatabase extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 5;
    private static final String DATABASE_NAME = "Alarms";
    private static final String TABLE_ALARM = "Alarms";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_HOUR = "hourOfDay";
    private static final String COLUMN_MINUTE = "minutes";
    private static final String COLUMN_TITLE = "title";
    private static final String COLUMN_STATE = "isOn";
//    private static final String COLUMN_REPEAT = "repeat";

    public SqliteDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE "
                + TABLE_ALARM + "(" + COLUMN_ID
                + " INTEGER PRIMARY KEY,"
                + COLUMN_HOUR + " INTEGER,"
                + COLUMN_MINUTE + " INTEGER,"
                + COLUMN_TITLE + " STRING,"
                + COLUMN_STATE + " STRING" + ")";
//                + COLUMN_REPEAT + " INTEGER" + ")";
        db.execSQL(CREATE_CONTACTS_TABLE);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ALARM);
        onCreate(db);
    }
    public ArrayList<Alarm> listAlarms() {
        String sql = "select * from " + TABLE_ALARM;
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<Alarm> storeAlarms = new ArrayList<>();
        Cursor cursor = db.rawQuery(sql, null);
        if (cursor.moveToFirst()) {
            do {
                int id = Integer.parseInt(cursor.getString(0));
                Integer hourOfDay = Integer.parseInt(cursor.getString(1));
                Integer minutes = Integer.parseInt(cursor.getString(2));
                String title = cursor.getString(3);
                String state = cursor.getString(4);
//                cursor.getInt()
                Log.d("Database", state);
                storeAlarms.add(new Alarm(id, hourOfDay, minutes, title, state.equals("true") ? true : false));
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        return storeAlarms;
    }

    public void addAlarm(Alarm newAlarm) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_HOUR, newAlarm.getHourOfDay());
        values.put(COLUMN_MINUTE, newAlarm.getMinutes());
        values.put(COLUMN_TITLE, newAlarm.getmTitle());
        values.put(COLUMN_STATE, newAlarm.getState().toString());
        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(TABLE_ALARM, null, values);
    }
    public void updateAlarm(Alarm alarm) {
        ContentValues values = new ContentValues();
        String weekDayString = new String();
        ArrayList<Integer> weekDay = alarm.getmWeekDay();
        values.put(COLUMN_HOUR, alarm.getHourOfDay());
        values.put(COLUMN_MINUTE, alarm.getMinutes());
        values.put(COLUMN_TITLE, alarm.getmTitle());
        values.put(COLUMN_STATE, alarm.getState().toString());
        SQLiteDatabase db = this.getWritableDatabase();
        Log.d("Database", alarm.getState().toString());
        db.update(TABLE_ALARM, values, COLUMN_ID + " = ?", new String[]{String.valueOf(alarm.getId())});
    }
    public void deleteAlarm(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_ALARM, COLUMN_ID + " = ?", new String[]{String.valueOf(id)});
    }
}