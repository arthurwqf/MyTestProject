package com.qingfeng.mytest.NoteCalendar;

/**
 * Created by WangQF on 2018/1/18 0018.
 */

public class ModelNote {
    int id;
    int weather_icon;
    String weather;
    String content;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getWeather_icon() {
        return weather_icon;
    }

    public void setWeather_icon(int weather_icon) {
        this.weather_icon = weather_icon;
    }

    public String getWeather() {
        return weather;
    }

    public void setWeather(String weather) {
        this.weather = weather;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
