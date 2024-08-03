package com.denglitong.apache_commons_demo.time;

import org.apache.commons.lang3.time.DateFormatUtils;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * @author litong.deng@foxmail.com
 * @date 2021/8/26
 */
public class DateFormatUtilsDemo {

    public static void main(String[] args) {
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        String pattern = "YYYY-MM-dd HH:mm:ss";

        // 2021-08-26 19:46:11
        System.out.println(DateFormatUtils.format(date, pattern));
        System.out.println(DateFormatUtils.format(date.getTime(), pattern));
        System.out.println(DateFormatUtils.format(calendar, pattern));

        // 2021-08-26 19:46:11
        System.out.println(DateFormatUtils.format(date, pattern, TimeZone.getDefault()));
        System.out.println(DateFormatUtils.format(date, pattern, Locale.getDefault()));
        System.out.println(DateFormatUtils.format(date, pattern, TimeZone.getDefault(), Locale.getDefault()));

        // 2021-08-26 11:46:11
        System.out.println(DateFormatUtils.formatUTC(date, pattern));
        System.out.println(DateFormatUtils.formatUTC(date, pattern, Locale.getDefault()));
        System.out.println(DateFormatUtils.formatUTC(date, pattern, Locale.JAPAN));
    }
}
