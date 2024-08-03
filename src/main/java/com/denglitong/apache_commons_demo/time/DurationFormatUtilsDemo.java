package com.denglitong.apache_commons_demo.time;

import org.apache.commons.lang3.time.DurationFormatUtils;
import org.apache.commons.lang3.time.DurationUtils;

import java.time.Duration;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

/**
 * @author litong.deng@foxmail.com
 * @date 2021/8/27
 */
public class DurationFormatUtilsDemo {

    public static void main(String[] args) {
        Duration duration = DurationUtils.toDuration(3, TimeUnit.MINUTES)
                .plusMillis(548)
                .plusSeconds(30)
                .plusHours(12)
                .plusDays(27);

        // 660:03:30.548
        System.out.println(DurationFormatUtils.formatDurationHMS(duration.toMillis()));
        // P0Y0M27DT12H3M30.548S
        System.out.println(DurationFormatUtils.formatDurationISO(duration.toMillis()));
        // 27 days 12 hours 3 minutes 30 seconds
        System.out.println(DurationFormatUtils.formatDurationWords(duration.toMillis(),
                false, false));

        String pattern = "YYYY-MM-dd HH:mm:ss";
        // YYYY-00-27 12:03:30
        System.out.println(DurationFormatUtils.formatDuration(duration.toMillis(), pattern));
        // YYYY-00-27 12:03:30
        System.out.println(DurationFormatUtils.formatDuration(duration.toMillis(), pattern, true));
        // YYYY-0-27 12:3:30
        System.out.println(DurationFormatUtils.formatDuration(duration.toMillis(), pattern, false));

        Duration duration1 = duration.plusHours(-8);
        // P0Y0M0DT8H0M0.000S
        System.out.println(DurationFormatUtils.formatPeriodISO(duration1.toMillis(), duration.toMillis()));
        // YYYY-00-00 08:00:00
        System.out.println(DurationFormatUtils.formatPeriod(duration1.toMillis(), duration.toMillis(), pattern));
        // YYYY-00-00 08:00:00
        System.out.println(DurationFormatUtils.formatPeriod(duration1.toMillis(), duration.toMillis(), pattern,
                true, TimeZone.getDefault()));
        // YYYY-0-0 8:0:0
        System.out.println(DurationFormatUtils.formatPeriod(duration1.toMillis(), duration.toMillis(), pattern,
                false, TimeZone.getDefault()));

        // 'P'yyyy'Y'M'M'd'DT'H'H'm'M's.SSS'S'
        System.out.println(DurationFormatUtils.ISO_EXTENDED_FORMAT_PATTERN);
        // P0000Y0M27DT4H3M30.548S
        System.out.println(DurationFormatUtils.formatDuration(duration1.toMillis(),
                DurationFormatUtils.ISO_EXTENDED_FORMAT_PATTERN));
    }
}
