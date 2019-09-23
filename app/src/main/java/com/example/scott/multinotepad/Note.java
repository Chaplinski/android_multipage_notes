package com.example.scott.multinotepad;

import android.util.Log;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

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
        this.date = newDate;
    }

    public String getCurrentDate(){
//        DateFormat df = new SimpleDateFormat("EEE MMM d, hh:mm a");
//        Date dateobj = new Date();
//        String dateString = df.format(dateobj);
        Long tsLong = System.currentTimeMillis()/1000;
        String ts = tsLong.toString();
        return ts;
    }

    public String toString() {
        return title + ": " + body;
    }

    public  String getDateCurrentTimeZone(String timestamp) {
        Long lTimestamp = Long.parseLong(timestamp);
        try{
            Calendar calendar = Calendar.getInstance();
            TimeZone tz = TimeZone.getTimeZone("CST");
            calendar.setTimeInMillis(lTimestamp * 1000);
            calendar.add(Calendar.MILLISECOND, tz.getOffset(calendar.getTimeInMillis()));
            SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM d, hh:mm a");
            Date currentTimeZone = (Date) calendar.getTime();
            return sdf.format(currentTimeZone);
        }catch (Exception e) {
        }
        return "";
    }

}