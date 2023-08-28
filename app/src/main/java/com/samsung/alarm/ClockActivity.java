package com.samsung.alarm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class ClockActivity extends AppCompatActivity {
    ImageView mAlarm, mTimer, mStopwatch;
    private TextView twdate,twtime;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clock);
        mStopwatch = findViewById(R.id.stopwatchTab);
        mAlarm = findViewById(R.id.alarmTab);
        mTimer = findViewById(R.id.timerTab);
        Intent stwIntent = new Intent(getApplicationContext(), StopwatchActivity.class);
        Intent alIntent = new Intent(getApplicationContext(), MainActivity.class);
        Intent tmIntent = new Intent(getApplicationContext(), TimerActivity.class);
        mStopwatch.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                startActivity(stwIntent);
            }
        });
        mAlarm.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                startActivity(alIntent);
            }
        });
        mTimer.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                startActivity(tmIntent);
            }
        });
        Thread myThread = null;

        Runnable runnable = new CountDownRunner();
        myThread= new Thread(runnable);
        myThread.start();
    }
    public void doWork() {
        runOnUiThread(new Runnable() {
            public void run() {
                try{
                    TextView txtCurrentDate= (TextView)findViewById(R.id.TWDATE);
                    Calendar c = Calendar.getInstance();
                    SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
                    String date = df.format(c.getTime());
                    txtCurrentDate.setText(date);


                    TextView txtCurrentTime= (TextView)findViewById(R.id.TWTIME);

                    SimpleDateFormat df1 = new SimpleDateFormat("HH");
                    String hour = df1.format(c.getTime());
                    int hours = Integer.parseInt(hour);

                    SimpleDateFormat df2 = new SimpleDateFormat("mm");
                    String minites = df2.format(c.getTime());
                    int minutes = Integer.parseInt(minites);

                    SimpleDateFormat df3 = new SimpleDateFormat("ss");
                    String second = df3.format(c.getTime());
                    int seconds = Integer.parseInt(second);

                    String curTime = hours + ":" + minutes + ":" + seconds;
                    txtCurrentTime.setText(curTime);
                }catch (Exception e) {}
            }
        });
    }


    class CountDownRunner implements Runnable{
        // @Override
        public void run() {
            while(!Thread.currentThread().isInterrupted()){
                try {
                    doWork();
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }catch(Exception e){
                }
            }
        }
    }
}