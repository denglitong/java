package com.denglitong.apache_commons_demo.time;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * @author litong.deng@foxmail.com
 * @date 2021/8/26
 */
public class DateUtilsDemo {

    public static void main(String[] args) throws ParseException {
        // 20:00
        Date date = new Date();
        // 21:00
        Date date1 = new Date(date.getTime() + 1000 * 60 * 60);
        System.out.println(DateUtils.isSameDay(date, date1)); // true
        System.out.println(DateUtils.isSameInstant(date, date1)); // false

        Calendar calendar = Calendar.getInstance();
        Calendar calendar1 = Calendar.getInstance(TimeZone.getDefault(), Locale.TAIWAN);
        System.out.println(DateUtils.isSameDay(calendar, calendar1)); // true
        System.out.println(DateUtils.isSameInstant(calendar, calendar1)); // true
        System.out.println(DateUtils.isSameLocalTime(calendar, calendar1)); // true

        String pattern = "YYYY-MM-dd HH:mm:ss";
        Date date2 = DateUtils.parseDate("2021-08-26", "YYYY-MM-dd");
        System.out.println(DateFormatUtils.format(date2, pattern)); // 2021-08-26 00:00:00

        date2 = DateUtils.addMonths(date2, 1);
        System.out.println(DateFormatUtils.format(date2, pattern)); // // 2021-09-26 00:00:00
        date2 = DateUtils.addDays(date2, 2);
        System.out.println(DateFormatUtils.format(date2, pattern)); // // 2021-09-28 00:00:00
        date2 = DateUtils.addHours(date2, 20);
        date2 = DateUtils.addMinutes(date2, 7);
        System.out.println(DateFormatUtils.format(date2, pattern)); // // 2021-09-28 20:07:00

        date2 = DateUtils.setYears(date2, 2020);
        System.out.println(DateFormatUtils.format(date2, pattern)); // // 2020-09-28 20:07:00
        // month index start from 0
        date2 = DateUtils.setMonths(date2, 1);
        System.out.println(DateFormatUtils.format(date2, pattern)); // 2020-02-28 20:07:00

        Calendar calendar2 = DateUtils.toCalendar(date2);
        System.out.println(DateFormatUtils.format(calendar2, pattern)); // 2020-02-28 20:07:00
        calendar2 = DateUtils.toCalendar(date2, TimeZone.getDefault());
        System.out.println(DateFormatUtils.format(calendar2, pattern)); // 2020-02-28 20:07:00

        calendar2 = DateUtils.round(calendar2, Calendar.HOUR);
        System.out.println(DateFormatUtils.format(calendar2, pattern)); // 2020-02-28 20:00:00
        calendar2.set(Calendar.SECOND, 29);
        System.out.println(DateFormatUtils.format(calendar2, pattern)); // 2020-02-28 20:00:29
        calendar2 = DateUtils.round(calendar2, Calendar.MINUTE);
        System.out.println(DateFormatUtils.format(calendar2, pattern)); // 2020-02-28 20:00:00
        calendar2.set(Calendar.SECOND, 30);
        calendar2 = DateUtils.round(calendar2, Calendar.MINUTE);
        System.out.println(DateFormatUtils.format(calendar2, pattern)); // 2020-02-28 20:01:00

        Date date3 = DateUtils.parseDate("2021-08-26 20:17:33", pattern);
        // 2021-08-26 20:17:33
        System.out.println(DateFormatUtils.format(DateUtils.truncate(date3, Calendar.SECOND), pattern));
        // 2021-08-26 20:17:00
        System.out.println(DateFormatUtils.format(DateUtils.truncate(date3, Calendar.MINUTE), pattern));
        // 2021-08-26 20:00:00
        System.out.println(DateFormatUtils.format(DateUtils.truncate(date3, Calendar.HOUR), pattern));
        // 2021-08-26 00:00:00
        System.out.println(DateFormatUtils.format(DateUtils.truncate(date3, Calendar.DAY_OF_MONTH), pattern));
        // 2021-08-01 00:00:00
        System.out.println(DateFormatUtils.format(DateUtils.truncate(date3, Calendar.MONTH), pattern));

        // 2021-08-26 20:17:34
        System.out.println(DateFormatUtils.format(DateUtils.ceiling(date3, Calendar.SECOND), pattern));
        // 2021-08-26 20:18:00
        System.out.println(DateFormatUtils.format(DateUtils.ceiling(date3, Calendar.MINUTE), pattern));
        // 2021-08-26 21:00:00
        System.out.println(DateFormatUtils.format(DateUtils.ceiling(date3, Calendar.HOUR), pattern));
        // 2021-09-01 00:00:00
        System.out.println(DateFormatUtils.format(DateUtils.ceiling(date3, Calendar.MONTH), pattern));

        System.out.println("************************************");
        Date date4 = DateUtils.parseDate("2021-08-01 00:00:00", pattern);
        // 日期所在周的起始日（周日，周日~周六为一周），到下个月的第一周截止（周日~周六为一周，即到下个月的第一个周六截止）
        // 2021-08-01 为周日
        // [2021-08-01 00:00:00, 2021-09-04 00:00:00]
        DateUtils.iterator(date4, DateUtils.RANGE_MONTH_SUNDAY).forEachRemaining(calendar3 -> {
            System.out.println(DateFormatUtils.format(calendar3, pattern));
        });
        System.out.println("************************************");

        System.out.println("------------------------------------");
        // 日期所在周的起始日（周一，周一~周日为一周），到下个月的第一周截止（周一，周一~周日为一周，即到下个月的第一个周日截止）
        // // 2021-08-01 为周日
        // [2021-07-26 00:00:00, 2021-09-05 00:00:00]
        DateUtils.iterator(date4, DateUtils.RANGE_MONTH_MONDAY).forEachRemaining(calendar3 -> {
            System.out.println(DateFormatUtils.format(calendar3, pattern));
        });
        System.out.println("------------------------------------");

        Date date5 = DateUtils.parseDate("2021-08-26 20:51:13.538", pattern + ".SSS");
        System.out.println(DateUtils.getFragmentInMilliseconds(date5, Calendar.SECOND)); // 538
        System.out.println(DateUtils.getFragmentInMilliseconds(date5, Calendar.MINUTE)); // 13 * 1000 + 538

        Date date6 = DateUtils.parseDate("2021-08-26 04:51:29", pattern);
        System.out.println(DateUtils.truncatedEquals(date5, date6, Calendar.HOUR)); // false
        System.out.println(DateUtils.truncatedEquals(date5, date6, Calendar.DAY_OF_MONTH)); // true
        System.out.println(DateUtils.truncatedCompareTo(date5, date6, Calendar.HOUR)); // 1
        System.out.println(DateUtils.truncatedCompareTo(date5, date6, Calendar.DAY_OF_MONTH)); // 0

        System.out.println(DateUtils.MILLIS_PER_SECOND);
        System.out.println(DateUtils.MILLIS_PER_MINUTE);
        System.out.println(DateUtils.MILLIS_PER_HOUR);
        System.out.println(DateUtils.MILLIS_PER_DAY);
    }
}
