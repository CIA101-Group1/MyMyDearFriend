package com.tibame.group1.common.utils;

import com.tibame.group1.common.exception.DateException;

import lombok.NonNull;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 時間操作共用方法
 *
 * @author Jimmy Kang
 */
public class DateUtils {

    /** 系統預設日期格式 */
    public static final String DEFAULT_DATE_FORMAT = "yyyyMMdd";

    /** 系統預設日期至時分格式 */
    public static final String DEFAULT_DATE_MINUTE_FORMAT = "yyyyMMddHHmm";

    /** 系統預設時間格式 */
    public static final String DEFAULT_TIME_FORMAT = "yyyyMMddHHmmss";

    /** 系統預設只有時間格式 */
    public static final String DEFAULT_ONLY_TIME_FORMAT = "HHmmss";

    /** 系統預設輸出日期格式 */
    public static final String DEFAULT_WEB_DATE_FORMAT = "yyyy-MM-dd";

    /** 系統預設輸出時間格式 */
    public static final String DEFAULT_WEB_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

    /** 系統預設輸出只有時間格式 */
    public static final String DEFAULT_WEB_ONLY_TIME_FORMAT = "HH:mm:ss";

    /**
     * 增加秒數(可帶負值)
     *
     * @param date 日期
     * @param second 增加秒數
     * @return 增加後日期
     */
    public static Date addSecond(@NonNull Date date, int second) {
        Calendar cal = dateToCalendar(date);
        cal.add(Calendar.SECOND, second);
        return cal.getTime();
    }

    /**
     * 增加分鐘(可帶負值)
     *
     * @param date 日期
     * @param minute 增加分鐘
     * @return 增加後日期
     */
    public static Date addMinute(@NonNull Date date, int minute) {
        Calendar cal = dateToCalendar(date);
        cal.add(Calendar.MINUTE, minute);
        return cal.getTime();
    }

    /**
     * 增加小時(可帶負值)
     *
     * @param date 日期
     * @param hour 增加小時
     * @return 增加後日期
     */
    public static Date addHour(@NonNull Date date, int hour) {
        Calendar cal = dateToCalendar(date);
        cal.add(Calendar.HOUR, hour);
        return cal.getTime();
    }

    /**
     * 增加日期天數(可帶負值)
     *
     * @param date 日期
     * @param dayCount 增加天數
     * @return 增加後日期
     */
    public static Date addDay(@NonNull Date date, int dayCount) {
        Calendar cal = dateToCalendar(date);
        cal.add(Calendar.DATE, dayCount);
        return cal.getTime();
    }

    /**
     * 增加日期月數(可帶負值)
     *
     * @param date 日期
     * @param monthCount 增加月數
     * @return 增加後日期
     */
    public static Date addMonth(@NonNull Date date, int monthCount) {
        Calendar cal = dateToCalendar(date);
        cal.add(Calendar.MONTH, monthCount);
        return cal.getTime();
    }

    /**
     * 增加日期年數(可帶負值)
     *
     * @param date 日期
     * @param yearCount 增加年數
     * @return 增加後日期
     */
    public static Date addYear(@NonNull Date date, int yearCount) {
        Calendar cal = dateToCalendar(date);
        cal.add(Calendar.YEAR, yearCount);
        return cal.getTime();
    }

    /**
     * 轉換date至Calendar
     *
     * @param date 日期
     * @return Calendar
     */
    public static Calendar dateToCalendar(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar;
    }

    /**
     * 日期字串格式轉換器
     *
     * @param dateString 日期字串
     * @param dateFormat 輸入日期格式
     * @param convertFormat 輸出日期格式
     * @return 格式化後日期
     */
    public static String stringToDateString(
            @NonNull String dateString, @NonNull String dateFormat, @NonNull String convertFormat)
            throws DateException {
        try {
            SimpleDateFormat format = new SimpleDateFormat(dateFormat);
            Date date = format.parse(dateString);

            return new SimpleDateFormat(convertFormat).format(date);
        } catch (ParseException e) {
            throw new DateException("時間格式有誤");
        }
    }

    /**
     * 日期格式轉換器(使用系統預設時間格式)
     *
     * @param date 日期
     * @return 轉換後字串
     */
    public static String dateToSting(@NonNull Date date) {
        return dateToSting(date, DEFAULT_TIME_FORMAT);
    }

    /**
     * 日期格式轉換器
     *
     * @param date 日期
     * @param convertFormat 轉換格式
     * @return 轉換後字串
     */
    public static String dateToSting(@NonNull Date date, @NonNull String convertFormat) {
        return new SimpleDateFormat(convertFormat).format(date);
    }

    /**
     * 日期字串轉日期格式(使用系統預設時間格式)
     *
     * @param dateString 日期字串
     * @return 日期
     */
    public static Date stringToDate(@NonNull String dateString) throws DateException {
        return stringToDate(dateString, DEFAULT_TIME_FORMAT);
    }

    /**
     * 日期字串轉日期格式
     *
     * @param dateString 日期字串
     * @param dateFormat 日期格式
     * @return 日期
     */
    public static Date stringToDate(@NonNull String dateString, @NonNull String dateFormat)
            throws DateException {
        if (dateString.length() != dateFormat.length()) {
            throw new DateException("時間格式不為" + dateFormat);
        }
        try {
            SimpleDateFormat format = new SimpleDateFormat(dateFormat);
            Date date;
            date = format.parse(dateString);
            return date;
        } catch (ParseException e) {
            throw new DateException("時間格式不為" + dateFormat);
        }
    }
}
