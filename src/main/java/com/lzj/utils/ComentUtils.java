package com.lzj.utils;

import com.lzj.domain.EmailObject;
import com.lzj.domain.User;
import com.lzj.exception.BusinessException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;

import javax.mail.internet.MimeMessage;
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
    public final static String HOST="http://localhost:8080";
    public final static String ARTICLE_PIC="./src/main/resources/static/articlepic";
    public static Date getCurrentTime(){
        Calendar calendar=Calendar.getInstance(Locale.CHINESE);

        return calendar.getTime();
    }
    public static void sendEmail(EmailObject object){
        JavaMailSenderImpl sender=new JavaMailSenderImpl();
        sender.setUsername(object.getUserName());
        sender.setPassword(object.getPassword());
        sender.setDefaultEncoding(object.getDefaultEncoding());
        sender.setHost(object.getHost());
        sender.send(new MimeMessagePreparator() {
            @Override
            public void prepare(MimeMessage mimeMessage) throws Exception {
                MimeMessageHelper message = new MimeMessageHelper(mimeMessage, true, "UTF-8");
                message.setFrom(object.getUserName());
                message.setTo(object.getSendTo());
                message.setSubject(object.getSubject());
                message.setText(object.getContent(), true);
            }
        });
    }
    public static void sureLogin(User user){
        if (user==null){
            throw  new BusinessException(232,"请登录");
        }
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
