package com.samsung.alarm;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.samsung.alarm.adapter.AlarmAdapter;
import com.samsung.alarm.components.TimePickerFragment;
import com.samsung.alarm.database.SqliteDatabase;
import com.samsung.alarm.model.Alarm;
import com.samsung.alarm.notification.AlertReceiver;
import com.samsung.alarm.services.Music;
import com.samsung.alarm.utils.AlarmListener;
import com.samsung.alarm.utils.AlarmUtils;
import com.samsung.alarm.view.ShowAllActivities;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener {
    RecyclerView mRView;
    AlarmAdapter mAdapter;
    AlarmListener mListener;
    FloatingActionButton mFab;
    ArrayList<Alarm> mList;
    ImageView mClock, mTimer, mStopwatch;
    SqliteDatabase mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRView = findViewById(R.id.alarm_list);
        mDatabase = new SqliteDatabase(this);
        mList = mDatabase.listAlarms();

//        mList.add(new Alarm(10, 24, "Test" ,"20/07"));
//        mList.add(new Alarm(10, 25, "Test" ,"20/07"));
//        mList.add(new Alarm(10, 26, "Test" ,"20/07"));
//        mList.add(new Alarm(10, 27, "Test" ,"20/07"));
        AlarmManager mAlarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        mAdapter = new AlarmAdapter(mList, this, mListener, mAlarmManager, mDatabase);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        mRView.setLayoutManager(llm);
        mRView.setAdapter(mAdapter);
        mFab = findViewById(R.id.fab);
        mFab.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                addAlarmDialog();
            }
        });
        mStopwatch = findViewById(R.id.stopwatchTab);
        mClock = findViewById(R.id.clockTab);
        mTimer = findViewById(R.id.timerTab);
        Intent stwIntent = new Intent(getApplicationContext(), StopwatchActivity.class);
        Intent clIntent = new Intent(getApplicationContext(), ClockActivity.class);
        Intent tmIntent = new Intent(getApplicationContext(), TimerActivity.class);
        mStopwatch.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                startActivity(stwIntent);
            }
        });
        mClock.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                startActivity(clIntent);
            }
        });
        mTimer.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                startActivity(tmIntent);
            }
        });
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY, hourOfDay);
        c.set(Calendar.MINUTE, minute);
        c.set(Calendar.SECOND, 0);

//        updateTimeText(c);
        AlarmManager mAlarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        AlarmUtils.startAlarm(c, getApplicationContext(), mAlarmManager);
    }

    private void addAlarmDialog() {
        final Integer[] mHour = new Integer[1];
        final Integer[] mMinutes = new Integer[1];
//        final ArrayList<Integer> mWeekDay = new ArrayList<Integer>(10);
        LayoutInflater inflater = LayoutInflater.from(this);
        View subView = inflater.inflate(R.layout.add_alarm, null);
        final EditText edtTitle = subView.findViewById(R.id.edt_alarm_title);
//        final CheckBox mCheckBoxMon = subView.findViewById(R.id.checkMon);
//        final CheckBox mCheckBoxTue = subView.findViewById(R.id.checkTue);
//        final CheckBox mCheckBoxWed = subView.findViewById(R.id.checkWed);
//        final CheckBox mCheckBoxThu = subView.findViewById(R.id.checkThu);
//        final CheckBox mCheckBoxFri = subView.findViewById(R.id.checkFri);
//        final CheckBox mCheckBoxSat = subView.findViewById(R.id.checkSat);
//        final CheckBox mCheckBoxSun = subView.findViewById(R.id.checkSun);
//        final EditText noField = subView.findViewById(R.id.enterPhoneNum);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final TimePicker timePicker = subView.findViewById(R.id.time_picker);
        builder.setTitle("Schedule Alarm");
        builder.setView(subView);
        builder.create();
        timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener(){

            @Override
            public void onTimeChanged(TimePicker timePicker, int hourOfDay, int minute) {
                mHour[0] = hourOfDay;
                mMinutes[0] = minute;
            }
        });
        builder.setPositiveButton("ADD ALARM", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                final String title = edtTitle.getText().toString();
//                final String ph_no = noField.getText().toString();
//                if (TextUtils.isEmpty(name)) {
//                    Toast.makeText(MainActivity.this, "Something went wrong. Check your input values", Toast.LENGTH_LONG).show();
//                }
//                else {
//                    Contacts newContact = new Contacts(name, ph_no);
//                    mDatabase.addContacts(newContact);
//                    finish();
//                    startActivity(getIntent());
//                }
//                mCheckBoxMon.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
//
//                    @Override
//                    public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
//                        if(isChecked) mWeekDay.add(2);
//                        else mWeekDay.removeIf(item -> item.equals(2));
//                    }
//                });
//                mCheckBoxTue.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
//
//                    @Override
//                    public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
//                        if(isChecked) mWeekDay.add(3);
//                        else mWeekDay.removeIf(item -> item.equals(3));
//                    }
//                });
//                mCheckBoxWed.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
//
//                    @Override
//                    public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
//                        if(isChecked) mWeekDay.add(4);
//                        else mWeekDay.removeIf(item -> item.equals(4));
//                    }
//                });
//                mCheckBoxThu.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
//
//                    @Override
//                    public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
//                        if(isChecked) mWeekDay.add(5);
//                        else mWeekDay.removeIf(item -> item.equals(5));
//                    }
//                });
//                mCheckBoxFri.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
//
//                    @Override
//                    public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
//                        if(isChecked) mWeekDay.add(6);
//                        else mWeekDay.removeIf(item -> item.equals(6));
//                    }
//                });
//                mCheckBoxSat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
//
//                    @Override
//                    public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
//                        if(isChecked) mWeekDay.add(7);
//                        else mWeekDay.removeIf(item -> item.equals(7));
//                        Log.d("Check", "Sat");
//                    }
//                });
//                mCheckBoxSun.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
//
//                    @Override
//                    public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
//                        if(isChecked) mWeekDay.add(8);
//                        else mWeekDay.removeIf(item -> item.equals(8));
//                    }
//                });
                Alarm newAlarm = new Alarm(mHour[0], mMinutes[0], title);
                mList.add(newAlarm);
                mDatabase.addAlarm(newAlarm);
                mAdapter.notifyDataSetChanged();
                String weekDayString = new String();
//                for(int i = 0; i < mWeekDay.size(); i++){
//                    weekDayString.concat(mWeekDay.get(i).toString());
//                }
                Calendar c = Calendar.getInstance();
                c.set(Calendar.HOUR_OF_DAY, newAlarm.getHourOfDay());
                c.set(Calendar.MINUTE, newAlarm.getMinutes());
                c.set(Calendar.SECOND, 0);
                Toast.makeText(MainActivity.this, "Alarm Added! ", Toast.LENGTH_LONG).show();
                AlarmManager mAlarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                Intent intent = new Intent(getApplicationContext(), AlertReceiver.class);
                intent.putExtra("state", "on");
                PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), mList.size() - 1, intent, PendingIntent.FLAG_IMMUTABLE);


                if (c.before(Calendar.getInstance())) {
                    c.add(Calendar.DATE, 1);
                }


                mAlarmManager.setExact(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), pendingIntent);
            }

        });
        builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(MainActivity.this, "Cancelled!", Toast.LENGTH_LONG).show();
            }
        });
        builder.show();
    }

}
