package com.example.mobile_immanueljoseph_2301852215.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class DateUtils {

    public static String dateFromServer(String time){
        Date date;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault());
        dateFormat.setTimeZone(TimeZone.getTimeZone("GMT+7"));
        try {
            date = dateFormat.parse(time);
            dateFormat.applyPattern("dd MMM yyyy hh:mm");
        } catch (ParseException e) {
            e.printStackTrace();
            return "";
        }
        return dateFormat.format(date);
    }
}
