package com.repomon.rocketdan.common.utils;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;

public class DateUtils {

    public static Date yearsAgo(){
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.YEAR, -1);
        return null;
    }

    public static Date fewDateAgo(Date date, int day){
        Calendar instance = Calendar.getInstance();
        instance.setTime(date);
        instance.add(Calendar.DATE, day);
        return Date.from(instance.toInstant());
    }

    public static LocalDate dateToLocalDate(Date date){
        return date.toInstant()
            .atZone(ZoneId.systemDefault())
            .toLocalDate();
    }
}
