package com.lzj.service;

import com.lzj.utils.WebPubUtils;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.Map;

/**
 * Created by li on 17-8-13.
 */
@Service
public class WebpubService {
    private final String DIR="./src/main/resources/access_token";
    public Map requestCodeURL(){
        try {
            String codeURL=WebPubUtils.getCodeURL();
            Map<String,String> map=WebPubUtils.readValueFromURL(codeURL);
            return map;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取全局配置的access_token
     * @return
     */
    public Map requestStaticAccessToken(){
      return WebPubUtils.readStaticAccessToken(DIR);
    }

    /**
     * 获取授权的access_token
     * @return
     */
    public Map requestAccessToken(String code){
        String accessTokenUrl=WebPubUtils.getWebAccessToken(code);
        try {
            return WebPubUtils.readValueFromURL(accessTokenUrl);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 拉取用户信息
     * @param code
     * @return
     */
    public Map requestUserInfo(String code){
        Map map=requestAccessToken(code);
        String accessToken= (String) map.get("access_token");
        String openId= (String) map.get("openId");
        String userInfoUrl=WebPubUtils.getUserInfo(accessToken,openId);
        try {
            return WebPubUtils.readValueFromURL(userInfoUrl);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
