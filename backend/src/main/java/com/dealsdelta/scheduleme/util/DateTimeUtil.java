package com.dealsdelta.scheduleme.util;


import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;

/**
 * Description : <Write class Description>
 * Author: kumar
 * Created on : 08/09/22
 */

public class DateTimeUtil {
    public static String getTime(LocalTime localTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        return localTime.format(formatter);
    }

    public static Calendar getCalenderInstance() {
        return Calendar.getInstance();
    }
}