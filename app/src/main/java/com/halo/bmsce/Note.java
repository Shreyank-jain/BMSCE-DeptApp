package com.halo.bmsce;

import java.util.Calendar;

public class Note {
    private String title;
    private String description;
    private String lastdate;

    public Note(){}

    public Note(String title,String description,String lastdate) {
        this.title = title;
        this.description = description;
        this.lastdate = lastdate;
    }
    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getLastdate() {
        return lastdate;
    }


}

