package com.denglitong.apache_commons_demo.time;

import org.apache.commons.lang3.time.DateUtils;
import org.apache.commons.lang3.time.DurationUtils;

import java.text.ParseException;
import java.time.Duration;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * @author litong.deng@foxmail.com
 * @date 2021/8/26
 */
public class DurationUtilsDemo {

    public static void main(String[] args) throws ParseException {
        String pattern = "YYYY-MM-dd HH:mm:ss.SSS";
        Date date = DateUtils.parseDate("2021-08-26 23:06:30.548", pattern);
        Date date1 = DateUtils.parseDate("2021-08-26 22:16:45.626", pattern);

        Duration duration = Duration.ofMillis(date.getTime() - date1.getTime());
        System.out.println(duration.toMillis()); // 2985000
        System.out.println(duration.toMinutes()); // 49
        System.out.println(duration.toHours()); // 0

        duration = duration.plusNanos(548);

        // mills: 2985000, nanos: 548
        DurationUtils.accept(DurationUtilsDemo::testAcceptDuration, duration);

        System.out.println(DurationUtils.getNanosOfMiili(duration)); // 548

        System.out.println(DurationUtils.isPositive(duration)); // true

        Duration duration1 = DurationUtils.toDuration(2, TimeUnit.MINUTES);
        System.out.println(duration1.toMillis()); // 120000
        System.out.println(DurationUtils.toMillisInt(duration1)); // 120000

        duration1 = null;
        duration1 = DurationUtils.zeroIfNull(duration1);
        System.out.println(duration1.toMillis()); // 0
    }

    private static void testAcceptDuration(Long mills, Integer nanos) {
        System.out.printf("mills: %d, nanos: %d\n", mills, nanos);
    }
}
