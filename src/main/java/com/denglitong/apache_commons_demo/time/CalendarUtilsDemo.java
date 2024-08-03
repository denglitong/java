package com.denglitong.apache_commons_demo.time;

import org.apache.commons.lang3.time.CalendarUtils;

import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

/**
 * @author litong.deng@foxmail.com
 * @date 2021/8/26
 */
public class CalendarUtilsDemo {

    public static void main(String[] args) {
        CalendarUtils instance = CalendarUtils.INSTANCE;
        // current calendar: month begin from 0: 2021 7 26
        System.out.println(instance.getYear() + " " + instance.getMonth() + " " + instance.getDayOfMonth());

        Calendar calendar = Calendar.getInstance(TimeZone.getDefault(), Locale.getDefault());
        CalendarUtils calendarUtils = new CalendarUtils(calendar);
        // 2021-7-26
        System.out.println(calendarUtils.getYear() +
                "-" + calendarUtils.getMonth() +
                "-" + calendarUtils.getDayOfMonth());
    }
}
