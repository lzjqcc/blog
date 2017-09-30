package com.lzj.utils;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by li on 17-8-9.
 */
public class ComentUtils {
    public final static String TYPE_REPLY="reply";
    public final static String TYPE_COMMENT="comment";
    public final static String ICON_DIR="./src/main/resources/static/icon";
    public static Date getCurrentTime(){
        Calendar calendar=Calendar.getInstance(Locale.CHINESE);

        return calendar.getTime();
    }
    public static void sendEmail(String email){

    }

    public static void closeStream(Closeable stream){
        if (stream!=null){
            try {
                stream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
