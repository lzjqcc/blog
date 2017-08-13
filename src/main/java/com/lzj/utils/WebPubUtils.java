package com.lzj.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.text.StrBuilder;
import org.springframework.web.client.RestTemplate;

import java.io.*;
import java.net.URLEncoder;
import java.util.Calendar;
import java.util.Map;

/**
 * Created by li on 17-8-13.
 */
public class WebPubUtils {
    private final static String APPID = "wxf509b4d3e16bc84c";
    private final static String SECRET = "1340472ee7127247f215367959e0e4d0";

    public static String getCodeURL() throws UnsupportedEncodingException {
        String url = URLEncoder.encode("http://b7eb9404.ngrok.io/webpub/index", "UTF-8");
        String codeURL = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=" + APPID + "&redirect_uri=" + url + "&response_type=code&scope=snsapi_userinfo&state=STATE#wechat_redirect";

        return codeURL;
    }

    public static Map readValueFromURL(String url) throws IOException {
        //ObjectMapper mapper=new ObjectMapper();
        RestTemplate restTemplate = new RestTemplate();
        String data = restTemplate.getForObject(url, String.class);
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(data, Map.class);
    }

    //https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code
    public static String getStaticAccessToken() {
        String accessTokenURL = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=" + APPID + "&secret=" + SECRET;
        return accessTokenURL;
    }

    public static String getWebAccessToken(String code) {
        String accessTokenURL = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=" + APPID + "&secret=" + SECRET + "&code=" + code + "&grant_type=authorization_code";
        return accessTokenURL;
    }

    public static String getUserInfo(String accessToken, String openId) {
        String userInfoURL = "https://api.weixin.qq.com/sns/userinfo?access_token=" + accessToken + "&openid=" + openId + "&";
        return userInfoURL;
    }

    /**
     * 先从acess_token.txt中读取access_token，要是没有则请求微信获取access_token
     * @param dir
     * @return
     */
    public static Map readStaticAccessToken(String dir) {
        File file = new File(dir);
        if (!file.exists()) {
            file.mkdir();
        }
        File accessTokenFile = new File(file, "access_token.txt");
        if (!accessTokenFile.exists()) {
            try {
                accessTokenFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        StrBuilder builder = new StrBuilder();
        String str = "";
        BufferedReader reader = null;
        Writer writer = null;
        try {
            reader = new BufferedReader(new InputStreamReader(new FileInputStream(accessTokenFile)));
            writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(accessTokenFile)));
            while ((str = reader.readLine()) != null) {
                builder.append(str);
            }
            if (StringUtils.isEmpty(builder.toString())) {
                return saveStaticTokenToFile(accessTokenFile);
            } else {
                ObjectMapper mapper = new ObjectMapper();
                Map<String, String> map = mapper.readValue(builder.toString(), Map.class);
                Long exit = Long.parseLong(map.get("expires_in"));
                Long pasetime = Long.parseLong(map.get("current_time"));
                Long currentTime = System.currentTimeMillis();
                if (((currentTime - pasetime) / 60 - exit) >7000) {
                    //缓存过期重新获取access_token,并保存
                    return saveStaticTokenToFile(accessTokenFile);
                }
                return map;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            close(reader, writer);
        }
        return null;
    }

    /**
     * 保存全局的access_token
     * @param accessTokenFile
     * @return
     */
    private static Map saveStaticTokenToFile(File accessTokenFile) {
        Writer writer = null;
        try {
            writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(accessTokenFile)));
            String accessTokenUrl = WebPubUtils.getStaticAccessToken();
            Map map = WebPubUtils.readValueFromURL(accessTokenUrl);
            map.put("current_time", System.currentTimeMillis());
            String accessToken = JSONObject.fromObject(map).toString();
            writer.write(accessToken);
            writer.flush();
            return map;

        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            close(null,writer);
        }
        return null;
    }

    private static void close(Reader reader, Writer writer) {
        try {
            if (reader != null) {
                reader.close();
                reader = null;
            }
            if (writer != null) {

                writer.close();
                writer = null;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
