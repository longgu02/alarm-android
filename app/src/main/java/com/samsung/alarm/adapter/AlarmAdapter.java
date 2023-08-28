package com.samsung.alarm.adapter;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.samsung.alarm.MainActivity;
import com.samsung.alarm.R;
import com.samsung.alarm.database.SqliteDatabase;
import com.samsung.alarm.model.Alarm;
import com.samsung.alarm.notification.AlertReceiver;
import com.samsung.alarm.utils.AlarmListener;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Calendar;

public class AlarmAdapter extends RecyclerView.Adapter {
    private ArrayList<Alarm> mList;
    private Context mContext;
    private AlarmListener mListener;
    private SqliteDatabase mDatabase;
    AlarmManager mAlarmManager;
    public AlarmAdapter(ArrayList<Alarm> mList, Context mContext, AlarmListener mListener, AlarmManager mAlarmManager, SqliteDatabase mDatabase) {
        this.mList = mList;
        this.mContext = mContext;
        this.mListener = mListener;
        this.mAlarmManager = mAlarmManager;
        this.mDatabase = mDatabase;
    }


    @NonNull
    @Override
    public AlarmViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.alarm_item, parent, false);
        AlarmViewHolder avh = new AlarmViewHolder(view);
        return avh;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Alarm alarm = mList.get(position);
        AlarmViewHolder viewHolder = (AlarmViewHolder) holder;
//        viewHolder.tvDay.setText(alarm.getmDay());
        viewHolder.tvTitle.setText(alarm.getmTitle());
        viewHolder.tvTime.setText(String.valueOf(alarm.getHourOfDay()) + ":" + (Integer.parseInt(String.valueOf(alarm.getMinutes())) <= 9 ? "0" + String.valueOf(alarm.getMinutes()) : String.valueOf(alarm.getMinutes())));
        viewHolder.swtState.setChecked(alarm.getState());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editAlarmDialog(position);
            }
        });
        ((AlarmViewHolder) holder).swtState.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){

            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    Calendar c = Calendar.getInstance();
                    c.set(Calendar.HOUR_OF_DAY, alarm.getHourOfDay());
                    c.set(Calendar.MINUTE, alarm.getMinutes());
                    c.set(Calendar.SECOND, 0);
                    // on below line we are setting text
                    // if switch is checked.
                    alarm.setState(true);
                    mDatabase.updateAlarm(alarm);
                    Toast.makeText(mContext, "Alarm On", Toast.LENGTH_SHORT).show();
                    startAlarm(c, position);
                } else {
                    // on below line we are setting text
                    // if switch is unchecked.
                    alarm.setState(false);
                    mDatabase.updateAlarm(alarm);
                    cancelAlarm(position);
                    Toast.makeText(mContext, "Alarm Off", Toast.LENGTH_SHORT).show();
                }
            }
        });
        ((AlarmViewHolder) holder).btnDelete.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                mList.remove(position);
                notifyDataSetChanged();
                if(alarm.getId() != null){
                    mDatabase.deleteAlarm(alarm.getId());
                }else{
                    ArrayList<Alarm> listAlarm = mDatabase.listAlarms();
                    mDatabase.deleteAlarm(listAlarm.get(position).getId());
                }
//                ((Activity) context).finish();
                Toast.makeText(mContext, "clicked delete on " +position, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    private void startAlarm(Calendar c, Integer requestCode) {
//        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(mContext, AlertReceiver.class);
        intent.putExtra("state", "on");
        PendingIntent pendingIntent = PendingIntent.getBroadcast(mContext, requestCode, intent, PendingIntent.FLAG_IMMUTABLE);


        if (c.before(Calendar.getInstance())) {
            c.add(Calendar.DATE, 1);
        }


        mAlarmManager.setExact(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), pendingIntent);
    }

    class AlarmViewHolder extends RecyclerView.ViewHolder{
        TextView tvDay;
        TextView tvTitle;
        TextView tvTime;
        Switch swtState;
        Button btnDelete;
        public AlarmViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDay = itemView.findViewById(R.id.alarm_day);
            tvTitle = itemView.findViewById(R.id.alarm_title);
            tvTime = itemView.findViewById(R.id.alarm_time);
            swtState = itemView.findViewById(R.id.swtState);
            btnDelete = itemView.findViewById(R.id.btnDelete);
        }
    }

    private void editAlarmDialog(Integer position) {
        final Integer[] mHour = new Integer[1];
        final Integer[] mMinutes = new Integer[1];
        Alarm mItem = mList.get(position);
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View subView = inflater.inflate(R.layout.add_alarm, null);
        final EditText edtTitle = subView.findViewById(R.id.edt_alarm_title);
        edtTitle.setText(mItem.getmTitle());
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
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
                Alarm mItem = mList.get(position);
                mItem.setHourOfDay(mHour[0]);
                mItem.setMinutes(mMinutes[0]);
                mItem.setmTitle(title);
                notifyDataSetChanged();
                Calendar c = Calendar.getInstance();
                c.set(Calendar.HOUR_OF_DAY,mItem.getHourOfDay());
                c.set(Calendar.MINUTE, mItem.getMinutes());
                c.set(Calendar.SECOND, 0);
                startAlarm(c, position);
                mDatabase.updateAlarm(mItem);
                Toast.makeText(mContext, "Add Alarm Hehe: " + String.valueOf(mHour[0])+":"+String.valueOf(mMinutes[0]), Toast.LENGTH_LONG).show();

            }
        });
        builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(mContext, "Task cancelled", Toast.LENGTH_LONG).show();
            }
        });
        builder.show();
    }

    private void cancelAlarm(Integer position) {
        Intent intent = new Intent(mContext, AlertReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(mContext, position, intent, PendingIntent.FLAG_IMMUTABLE);
        intent.putExtra("state", "off");
        mContext.sendBroadcast(intent);
        mAlarmManager.cancel(pendingIntent);
    }
}
