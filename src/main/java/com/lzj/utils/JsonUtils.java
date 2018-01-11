package com.lzj.utils;

import com.lzj.domain.Account;
import net.sf.json.JSONObject;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.converter.json.MappingJacksonInputMessage;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Map;

/**
 * Created by li on 17-8-10.
 */
public class JsonUtils {

    public static String toJson(Object object){
        return JSONObject.fromObject(object).toString();
    }
    public  static <T> T toBean(JSONObject jsonObject,Class<T> clazz){
        return (T) JSONObject.toBean(jsonObject,clazz);
    }
    public static <T> T mapToOtherBean(Map map,Class<T> clazz){
        return toBean(JSONObject.fromObject(map),clazz);
    }
    public static <T> T requestToObject(HttpServletRequest request,Class<T> clazz) {
        HttpMessageConverter messageConverter = new MappingJackson2HttpMessageConverter();
        try {
            return (T) messageConverter.read(clazz, new MappingJacksonInputMessage(request.getInputStream(), null));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
