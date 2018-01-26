package com.qingfeng.mytest.NoteCalendar;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.qingfeng.mytest.BR;

/**
 * Created by WangQF on 2018/1/18 0018.
 */

public class ModelNote extends BaseObservable{
    int id;
    int year;
    int month;
    int day;
    String weather;
    String content;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Bindable
    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
        notifyPropertyChanged(BR.year);
    }

    @Bindable
    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
        notifyPropertyChanged(BR.month);
    }

    @Bindable
    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
        notifyPropertyChanged(BR.day);
    }

    @Bindable
    public String getWeather() {
        return weather;
    }

    public void setWeather(String weather) {
        this.weather = weather;
        notifyPropertyChanged(BR.weather);
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
