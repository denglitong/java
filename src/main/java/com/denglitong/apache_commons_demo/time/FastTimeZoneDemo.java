package com.denglitong.apache_commons_demo.time;

import org.apache.commons.lang3.time.FastTimeZone;

import java.util.TimeZone;

/**
 * @author litong.deng@foxmail.com
 * @date 2021/8/27
 */
public class FastTimeZoneDemo {

    public static void main(String[] args) {
        TimeZone timeZone = FastTimeZone.getGmtTimeZone();
        System.out.println(timeZone);

        timeZone = FastTimeZone.getGmtTimeZone("GMT-8");
        // [GmtTimeZone id="GMT-08:00",offset=-28800000]
        System.out.println(timeZone);

        String timeZoneId = timeZone.getID();
        TimeZone timeZone1 = FastTimeZone.getTimeZone(timeZoneId);
        // [GmtTimeZone id="GMT-08:00",offset=-28800000]
        System.out.println(timeZone1);
    }
}
