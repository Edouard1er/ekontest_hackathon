package com.example.ekontest_hackathon;

import android.text.format.DateFormat;

import java.util.Calendar;
import java.util.Locale;

public class ConvertDateTimeModel {
    public ConvertDateTimeModel() {
    }
    public String getDate(long time) {
        Calendar cal = Calendar.getInstance(Locale.ENGLISH);
        cal.setTimeInMillis(time);
        String date = DateFormat.format("hh:mm", cal).toString();
        return date;
    }
    public String getDateWithOutTime(long time) {
        Calendar cal = Calendar.getInstance(Locale.ENGLISH);
        cal.setTimeInMillis(time);
        String date = DateFormat.format("dd-MM-yyyy", cal).toString();
        return date;
    }
}
