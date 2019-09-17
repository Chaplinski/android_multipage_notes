package com.example.scott.multinotepad;

import android.util.Log;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Note {

    private String title;
    private String body;
    private String date;

    private static int ctr = 0;


    public String getTitle() {
        return title;
    }

    public void setTitle(String newTitle) { this.title = newTitle; }

    public String getBody() {
        return body;
    }

    public void setBody(String newBody) { this.body = newBody; }

    public String getDate() { return date; }

    public void setDate(String newDate){
        DateFormat df = new SimpleDateFormat("EEE MMM d, hh:mm a");
        Date dateobj = new Date();
        String dateString = df.format(dateobj);
        Log.d("scooter", "setDate: " + dateString);
        this.date = dateString;
    }

    public String toString() {
        return title + ": " + body;
    }

}