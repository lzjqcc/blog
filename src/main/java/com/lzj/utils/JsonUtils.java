package com.lzj.utils;

import net.sf.json.JSONObject;

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
}
