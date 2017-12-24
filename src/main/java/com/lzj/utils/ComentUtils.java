package com.lzj.utils;

import com.lzj.domain.Account;
import com.lzj.domain.EmailObject;
import com.lzj.exception.BusinessException;
import com.lzj.exception.SystemException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;

import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by li on 17-8-9.
 */
public class ComentUtils {
    private final static List<SystemException> list = new CopyOnWriteArrayList<SystemException>();

    public final static String ICON_DIR="./src/main/resources/static/icon";
    public final static String HOST="http://localhost:8080";
    public final static String ARTICLE_PIC="./src/main/resources/static/articlepic";
    public final static String PICTURE_DIR="./src/main/resources/static/picture";

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

    /**
     * 保存系统系统在内存中，等管理员登录将系统异常发送给管理员
     * @param ex
     */
    public static void saveSystemException(SystemException ex){
        list.add(ex);
    }
    public static void cleanSystemException(){
        list.clear();
    }
    public static List<SystemException> getSystemExceptions(){
        return list;
    }
    public static void removeSystemEx(int index){
        list.remove(index);
    }
    public static void sureLogin(Account account){
        if (account==null){
            throw  new BusinessException(232,"请登录");
        }
    }
    public static Map<String,Object> buildMessageCondition(Integer toId,Boolean type,Byte flag ){
        Map<String, Object> map = new HashMap<>();
        if (toId != null){
            map.put("to_user_id",toId);
        }
        if (type != null){
            map.put("type",type);
        }
        if (flag != null){
            map.put("flag",flag);
        }
        return map;
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
    public static String getImageURL(String local) {
        return ComentUtils.HOST+local.split("static")[1];
    }
    /**
     * 获取token防止用户多次提交
     */
    public static String getToken(){
        String uuid = UUID.randomUUID().toString();
        return uuid.replace("-","");

    }
    public static Boolean vailedToken(HttpServletResponse response, HttpServletRequest request){
        PrintWriter writer = null;
        try {
            String token = (String) request.getAttribute("token");
            if (token == null) {
                token = ComentUtils.getToken();
                writer = response.getWriter();
                Map<String, String> map = new HashMap<>();
                map.put("token", token);
                writer.write(JsonUtils.toJson(map));
                return true;
            }else {
                return false;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            ComentUtils.closeStream(writer);
        }
        return null;
    }

}
