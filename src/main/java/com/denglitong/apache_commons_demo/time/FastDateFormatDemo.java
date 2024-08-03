package com.denglitong.apache_commons_demo.time;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.commons.lang3.time.FastDateFormat;

import java.text.ParseException;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * @author litong.deng@foxmail.com
 * @date 2021/8/27
 */
public class FastDateFormatDemo {

    public static void main(String[] args) throws ParseException {
        String pattern = "YYYY-MM-dd HH:mm:ss";
        Date date = DateUtils.parseDate("2021-08-27 20:09:30", pattern);

        FastDateFormat dateFormat = FastDateFormat.getInstance();
        System.out.println(dateFormat.format(date)); // 21-8-27 下午8:09

        dateFormat = FastDateFormat.getInstance(pattern + ".SSS", TimeZone.getDefault(), Locale.TAIWAN);
        System.out.println(dateFormat.format(date)); // 2021-08-27 20:09:30.000

        dateFormat = FastDateFormat.getDateInstance(FastDateFormat.FULL);
        System.out.println(dateFormat.format(date)); // 2021年8月27日 星期五

        dateFormat = FastDateFormat.getDateInstance(FastDateFormat.LONG);
        System.out.println(dateFormat.format(date)); // 2021年8月27日

        dateFormat = FastDateFormat.getDateInstance(FastDateFormat.MEDIUM);
        System.out.println(dateFormat.format(date)); // 2021-8-27

        dateFormat = FastDateFormat.getDateInstance(FastDateFormat.SHORT);
        System.out.println(dateFormat.format(date)); // 21-8-27

        dateFormat = FastDateFormat.getTimeInstance(FastDateFormat.FULL);
        System.out.println(dateFormat.format(date)); // 下午08时09分30秒 CST
        dateFormat = FastDateFormat.getTimeInstance(FastDateFormat.FULL, TimeZone.getDefault(), Locale.CHINA);
        System.out.println(dateFormat.format(date)); // 下午08时09分30秒 CST

        dateFormat = FastDateFormat.getTimeInstance(FastDateFormat.LONG);
        System.out.println(dateFormat.format(date)); // 下午08时09分30秒

        dateFormat = FastDateFormat.getTimeInstance(FastDateFormat.MEDIUM);
        System.out.println(dateFormat.format(date)); // 20:09:30

        dateFormat = FastDateFormat.getTimeInstance(FastDateFormat.SHORT);
        System.out.println(dateFormat.format(date)); // 下午8:09

        dateFormat = FastDateFormat.getDateTimeInstance(FastDateFormat.FULL, FastDateFormat.FULL);
        System.out.println(dateFormat.format(date)); // 2021年8月27日 星期五 下午08时09分30秒 CST

        dateFormat = FastDateFormat.getDateTimeInstance(FastDateFormat.LONG, FastDateFormat.LONG);
        System.out.println(dateFormat.format(date)); // 2021年8月27日 下午08时09分30秒

        dateFormat = FastDateFormat.getDateTimeInstance(FastDateFormat.MEDIUM, FastDateFormat.MEDIUM);
        System.out.println(dateFormat.format(date)); // 2021-8-27 20:09:30

        dateFormat = FastDateFormat.getDateTimeInstance(FastDateFormat.SHORT, FastDateFormat.SHORT);
        System.out.println(dateFormat.format(date)); // 21-8-27 下午8:09

        dateFormat = FastDateFormat.getInstance(pattern);
        Date date1 = dateFormat.parse("2021-08-27 20:23:33");
        System.out.println(DateFormatUtils.format(date1, pattern)); // 2021-08-27 20:23:33
        System.out.println(dateFormat.getMaxLengthEstimate()); // 19
        System.out.println("2021-08-27 20:23:33".length()); // 19

        System.out.println(dateFormat.getPattern());
        // sun.util.calendar.ZoneInfo[id="Asia/Shanghai",offset=28800000,dstSavings=0,useDaylight=false,transitions=31,lastRule=null]
        System.out.println(dateFormat.getTimeZone());
        // zh_CN
        System.out.println(dateFormat.getLocale());
    }
}
