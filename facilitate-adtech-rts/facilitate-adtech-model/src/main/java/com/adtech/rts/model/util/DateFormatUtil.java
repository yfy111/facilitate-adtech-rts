package com.adtech.rts.model.util;

import cn.hutool.core.date.DateUtil;
import com.adtech.rts.model.stic.DateFormatFinal;
import com.mongodb.BasicDBObject;
import sun.awt.geom.AreaOp;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * 时间
 * 1= 明天
 * 0 = 当天
 * -1 = 昨天
 */
public class DateFormatUtil {
    public static Date getDay(Date date,Integer i) {
        if(date == null){
            throw new NullPointerException();
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, i);//明天
        return calendar.getTime();
    }

    /**
     * 根据小时数获取时间
     * @param date
     * @param i
     * @return
     */
    public static Date getDayHours(Date date,Integer i) {
        if(date == null){
            throw new NullPointerException();
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.HOUR_OF_DAY, i);
        return calendar.getTime();
    }


    public static Date getDayByHour(Date date,int i){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, i);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        Date start = calendar.getTime();
        return start;
    }

    public static Date getISO8601TimestampDate(Date date,int i){
        SimpleDateFormat format = DateFormatFinal.YYYY_MM_DD_HH_MM_SS;
        format.setCalendar(new GregorianCalendar(new SimpleTimeZone(0, "GMT")));
        Date date1 = null;
        try {
            date1 = format.parse(DateUtil.formatDateTime(getDayByHour(date,i)));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date1;
    }

    /**
     * 将字符串转换为date
     * @param time
     * @return
     */
    public static Date getISO8601TimestampDate(String time){
        return DateUtil.parse(time).toJdkDate();
    }





//    public static Date dateToISODate(Date dateStr){
//        //T代表后面跟着时间，Z代表UTC统一时间
//        SimpleDateFormat format =
//                new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
//        format.setCalendar(new GregorianCalendar(new SimpleTimeZone(0, "GMT")));
//        String isoDate = format.format(dateStr);
//        try {
//            return format.parse(isoDate);
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }

    public static void main(String[] args) {
        Date date = new Date();
        System.err.println(DateUtil.formatDateTime(DateFormatUtil.getDayByHour(date, -24)));
        System.err.println(DateUtil.formatDateTime(DateFormatUtil.getDayByHour(date, 0)));
    }
}


