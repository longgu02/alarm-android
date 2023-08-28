package com.samsung.alarm.model;

import java.sql.Array;
import java.util.ArrayList;

public class Alarm {
    private Integer id;
    private Integer hourOfDay;
    private Integer minutes;
    private String mTitle;
    private ArrayList<Integer> mWeekDay;
    private Boolean state;


    public Alarm(Integer id, Integer hourOfDay, Integer minutes, String mTitle, Boolean state) {
        this.id = id;
        this.hourOfDay = hourOfDay;
        this.minutes = minutes;
        this.mTitle = mTitle;
        this.state = state;
    }

    public Alarm(Integer hourOfDay, Integer minutes, String mTitle) {
        this.hourOfDay = hourOfDay;
        this.minutes = minutes;
        this.mTitle = mTitle;
        this.state = true;
    }

    public ArrayList<Integer> getmWeekDay() {
        return mWeekDay;
    }

    public void setmWeekDay(ArrayList<Integer> mWeekDay) {
        this.mWeekDay = mWeekDay;
    }

    public Boolean getState() {
        return state;
    }

    public void setState(Boolean state) {
        this.state = state;
    }

    public Integer getHourOfDay() {
        return hourOfDay;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setHourOfDay(Integer hourOfDay) {
        this.hourOfDay = hourOfDay;
    }

    public Integer getMinutes() {
        return minutes;
    }

    public void setMinutes(Integer minutes) {
        this.minutes = minutes;
    }

    public String getmTitle() {
        return mTitle;
    }

    public void setmTitle(String mTitle) {
        this.mTitle = mTitle;
    }
}
