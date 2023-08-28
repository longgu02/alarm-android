package com.samsung.alarm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

public class TimerActivity extends AppCompatActivity {
    ImageView mClock, mAlarm, mStopwatch;

    private EditText mEditTextInput;
    private TextView mTextViewCountDown;
    private Button mButtonSet;
    private long mStartTimeInMillis;

    private Button mButtonStartPause;
    private Button mButtonReset;

    private CountDownTimer mCountDownTimer;

    private boolean mTimerRunning;

    private long mTimeLeftInMillis = mStartTimeInMillis;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);
        mStopwatch = findViewById(R.id.stopwatchTab);
        mAlarm = findViewById(R.id.alarmTab);
        mClock = findViewById(R.id.clockTab);
        Intent stwIntent = new Intent(getApplicationContext(), StopwatchActivity.class);
        Intent alIntent = new Intent(getApplicationContext(), MainActivity.class);
        Intent clIntent = new Intent(getApplicationContext(), ClockActivity.class);
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
        mClock.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                startActivity(clIntent);
            }
        });
        mEditTextInput = findViewById(R.id.time_input);
        mTextViewCountDown = findViewById(R.id.tvTimer);
        mButtonSet = findViewById(R.id.button_set);
        mButtonReset = findViewById(R.id.btn_reset);
        mButtonStartPause = findViewById(R.id.btn_start_pause);



        mButtonSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String input = mEditTextInput.getText().toString();
                if (input.length() == 0){
                    Toast.makeText(TimerActivity.this, "Field can't be empty", Toast.LENGTH_SHORT).show();
                    return;
                }
                long milliInput = Long.parseLong(input) * 60000;
                if (milliInput == 0){
                    Toast.makeText(TimerActivity.this, "Please enter a positive number", Toast.LENGTH_SHORT).show();
                }
                setTime(milliInput);
                mEditTextInput.setText("");
//                mEditTextInput.setVisibility(View.INVISIBLE);
//                mButtonSet.setVisibility(View.INVISIBLE);
            }
        });
        mButtonStartPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mTimerRunning){
                    pauseTimer();
                } else {
                    startTimer();
                }
            }
        });

        mButtonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetTimer();
            }
        });
        updateCountDownText();
    }
    private void setTime(long milliseconds){
        mStartTimeInMillis = milliseconds;
        resetTimer();
    }
    private void startTimer(){
        mCountDownTimer = new CountDownTimer(mTimeLeftInMillis, 1000) {
            @Override
            public void onTick(long l) {
                mTimeLeftInMillis = l;
                updateCountDownText();
            }

            @Override
            public void onFinish() {
                mTimerRunning = false;
                mButtonStartPause.setText("Start");
                mButtonStartPause.setVisibility(View.INVISIBLE);
                mButtonReset.setVisibility(View.VISIBLE);
            }
        }.start();
        mTimerRunning = true;
        mButtonStartPause.setText("pause");
        mButtonReset.setVisibility(View.INVISIBLE);
    }

    private void pauseTimer(){
        mCountDownTimer.cancel();
        mTimerRunning = false;
        mButtonStartPause.setText("Start");
        mButtonReset.setVisibility(View.VISIBLE);
    }

    private void resetTimer(){
        mTimeLeftInMillis = mStartTimeInMillis;
        updateCountDownText();
        mButtonReset.setVisibility(View.INVISIBLE);
        mButtonStartPause.setVisibility(View.VISIBLE);
    }

    private void updateCountDownText(){
        int hours = (int) (mTimeLeftInMillis / 1000) / 3600;
        int minutes = (int) ((mTimeLeftInMillis / 1000) % 3600) / 60;
        int seconds = (int) (mTimeLeftInMillis / 1000) % 60;
        String timeLeftFormatted;
        if (hours < 0){
            timeLeftFormatted = String.format(Locale.getDefault(),"%02d:%02d", minutes, seconds);
        } else {
            timeLeftFormatted = String.format(Locale.getDefault(),"%d:%02d:%02d", hours, minutes, seconds);
        }

        mTextViewCountDown.setText(timeLeftFormatted);
    }
}