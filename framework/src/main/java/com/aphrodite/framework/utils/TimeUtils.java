package com.aphrodite.framework.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * @Author szh
 * @Date 2019-09-25
 * @Description
 */
public class TimeUtils {
    /**
     * 年月日格式*中文间隔
     */
    public static final String FORMAT_CHINESE_ONE = "yyyy年MM月dd日 HH:mm";
    public static final String FORMAT_CHINESE_TWO = "yyyy年MM月dd日";
    public static final String FORMAT_CHINESE_THREE = "MM月dd日";
    /**
     * 年月日格式*特殊符号间隔
     */
    public static final String FORMAT_SPECIAL_SYMBOL_ONE = "yyyy.MM.dd";
    /**
     * 时钟格式
     */
    public static final String FORMAT_CLOCK_ONE = "HH:mm";

    /**
     * 时间戳转日期格式
     *
     * @param milSecond 毫秒
     * @param pattern
     * @return
     */
    public static String msToDateFormat(long milSecond, String pattern) {
        if (milSecond < 0) {
            return "";
        }

        Date date = new Date(milSecond);
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        return format.format(date);
    }

    /**
     * 秒->01:05
     *
     * @param mRecordSeconds
     * @return
     */
    public static String convertTime(int mRecordSeconds) {
        int minute = 0, second = 0;
        if (mRecordSeconds >= 60) {
            minute = mRecordSeconds / 60;
            second = mRecordSeconds % 60;
        } else {
            second = mRecordSeconds;
        }
        String timeTip = "data/0" + minute + ":" + (second < 10 ? "data/0" + second : second + "");
        return timeTip;
    }

    /**
     * 01:05->秒
     *
     * @param timeTip
     * @return
     */
    public static int convertTime(String timeTip) {
        int time = 0;
        String[] split = timeTip.split("\\:");
        if (ObjectUtils.isEmpty(split)) {
            return time;
        }
        try {
            int minutes = Integer.valueOf(split[0]);
            int seconds = Integer.valueOf(split[1].substring(0, 2));
            time = minutes * 60 + seconds;
        } catch (NumberFormatException e) {

        }
        return time;
    }

    /**
     * 比较两个时间戳，单位：ms
     *
     * @param msOne
     * @param msTwo
     * @param timeZone
     * @return
     */
    public static boolean isSameDay(long msOne, long msTwo, TimeZone timeZone) {
        return Math.abs(msOne - msTwo) < 24 * 60 * 60 * 1000 && secToAbsoluteDays(msOne, timeZone) == secToAbsoluteDays(msTwo, timeZone);
    }

    private static long secToAbsoluteDays(long ms, TimeZone timeZone) {
        return (ms + timeZone.getOffset(ms)) / (24 * 60 * 60 * 1000);
    }

}
