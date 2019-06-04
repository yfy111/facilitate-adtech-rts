package com.adtech.rts.model.stic;

import cn.hutool.core.date.format.FastDateFormat;

import java.text.SimpleDateFormat;

public class DateFormatFinal {
    //时间格式
    public static final FastDateFormat NORM_DATETIME_MINUTE_FORMAT = FastDateFormat.getInstance("yyyy-MM-dd HH");
    public static final SimpleDateFormat YYYY_MM_DD_HH_MM_SS = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public static final SimpleDateFormat YYYY_MM_DD_HH_MM_SS_Z = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
}
