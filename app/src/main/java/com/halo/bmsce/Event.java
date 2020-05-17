package com.halo.bmsce;

import java.util.Calendar;

public class Event {
    private String title;
    private String year;
    private String date;
    private String month;
    private String type;

    public Event(){}

    public Event(String title,String month,String date,String year,String type){
        this.title=title;
        this.year=year;
        this.date=date;
        this.month=month;
        this.type=type;
    }

    public String getTitle() {
        return title;
    }

    public String getYear() {
        return year;
    }

    public String getDate() {
        return date;
    }

    public String getMonth() {
        return month;
    }

    public String getType() {
        return type;
    }
}
