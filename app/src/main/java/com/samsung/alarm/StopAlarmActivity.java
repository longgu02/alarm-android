package com.samsung.alarm;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.samsung.alarm.notification.AlertReceiver;

import java.util.Random;

public class StopAlarmActivity extends AppCompatActivity {
    Button mButton1, mButton2, mButton3, mButton4, mButton5, mButton6, mButton7, mButton8, mButton9, mButton0;
    Button mButtonSend, mButtonClear, mButtonRandom;
    TextView textViewInput, textViewRandom;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stop_alarm);
        mButton0 = findViewById(R.id.button_0);
        mButton1 = findViewById(R.id.button_1);
        mButton2 = findViewById(R.id.button_2);
        mButton3 = findViewById(R.id.button_3);
        mButton4 = findViewById(R.id.button_4);
        mButton5 = findViewById(R.id.button_5);
        mButton6 = findViewById(R.id.button_6);
        mButton7 = findViewById(R.id.button_7);
        mButton8 = findViewById(R.id.button_8);
        mButton9 = findViewById(R.id.button_9);
        mButtonClear = findViewById(R.id.button_clear);
        mButtonRandom = findViewById(R.id.button_random);
        mButtonSend = findViewById(R.id.button_send);
        textViewInput = findViewById(R.id.textview_input);
        textViewRandom = findViewById(R.id.textview_random);
        Random random = new Random();
        final int value = random.nextInt(1000000) + 1;
        //textViewRandom.setText("" + value);
        mButtonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (textViewInput.getText().equals(textViewRandom.getText())) {
                    Toast.makeText(getApplicationContext(), "CONGRATULATION FOR TURNING OFF THE ALARM " , Toast.LENGTH_LONG).show();
                    AlarmManager mAlarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                    Intent intent = new Intent(getApplicationContext(), AlertReceiver.class);
                    PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 0, intent, PendingIntent.FLAG_IMMUTABLE);
                    intent.putExtra("state", "off");
                    sendBroadcast(intent);
                    mAlarmManager.cancel(pendingIntent);
                    Intent alIntent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(alIntent);
                }
                else {
                    Toast.makeText(getApplicationContext(), "PLEASE ENTER AGAIN " , Toast.LENGTH_LONG).show();
                }
            }
        });
        mButtonRandom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textViewRandom.setText("" + value);
            }
        });
        mButtonClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textViewInput.setText("");
            }
        });
        mButton0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textViewInput.setText(textViewInput.getText() + "0");
            }
        });
        mButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textViewInput.setText(textViewInput.getText() + "1");
            }
        });
        mButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textViewInput.setText(textViewInput.getText() + "2");
            }
        });
        mButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textViewInput.setText(textViewInput.getText() + "3");
            }
        });
        mButton4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textViewInput.setText(textViewInput.getText() + "4");
            }
        });
        mButton5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textViewInput.setText(textViewInput.getText() + "5");
            }
        });
        mButton6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textViewInput.setText(textViewInput.getText() + "6");
            }
        });
        mButton7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textViewInput.setText(textViewInput.getText() + "7");
            }
        });
        mButton8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textViewInput.setText(textViewInput.getText() + "8");
            }
        });
        mButton9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textViewInput.setText(textViewInput.getText() + "9");
            }
        });
    }
    public void putNumberToView(String num) {
        textViewInput.setText(num + "");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}