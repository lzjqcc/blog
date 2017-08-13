package com.lzj.utils;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by li on 17-8-9.
 */
public class ComentUtils {
    public final static String TYPE_REPLY="reply";
    public final static String TYPE_COMMENT="comment";
    public static Date getCurrentTime(){
        Calendar calendar=Calendar.getInstance(Locale.CHINESE);

        return calendar.getTime();
    }
}
