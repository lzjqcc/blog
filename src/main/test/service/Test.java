package service;

import com.lzj.domain.User;
import com.lzj.utils.JsonUtils;
import net.sf.json.JSONObject;
import org.apache.commons.lang.text.StrBuilder;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.File;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

/**
 * Created by li on 17-8-10.
 */
public class Test {
    @org.junit.Test
    public void testJson(){
        User user=new User();
        user.setEmail("dd");
        user.setPassword("123");
        User user1=JsonUtils.toBean(JSONObject.fromObject(user),User.class);
        System.out.println(user1.getEmail());
    }
    @org.junit.Test
    public void testSigure() throws UnsupportedEncodingException {
        try {
            String token="lzjqcc";
            String timestamp="1502589756";
            String nonce="2469502882";
            String str=token+timestamp+nonce;
            StrBuilder builder=new StrBuilder();
            MessageDigest digest=MessageDigest.getInstance("SHA1");
            digest.update(str.getBytes("utf-8"));
            byte[]bytes=digest.digest();
            for (byte b:bytes){
                builder.append(Integer.toHexString(b));
            }
            System.out.println(builder.toString());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }
    //9af759b5fbe502fcc349bc3f514a7f1effcfe712
    @org.junit.Test
    public void testWebSignuer(){
        String token="lzjqcc";
        String timestamp="1502589756";
        String nonce="2469502882";
        String[] arr=new String[]{token,timestamp,nonce};
        String str="";
        Arrays.sort(arr);
        for (String s:arr){
            str+=s;
        }
        System.out.println(str);
      System.out.println(getSha1(str));
    }
    public  String getSha1(String str){
        if(str==null||str.length()==0){
            return null;
        }
        char hexDigits[] = {'0','1','2','3','4','5','6','7','8','9',
                'a','b','c','d','e','f'};
        try {
            MessageDigest mdTemp = MessageDigest.getInstance("SHA1");
            mdTemp.update(str.getBytes("UTF-8"));

            byte[] md = mdTemp.digest();

            int j = md.length;
            char buf[] = new char[j*2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                buf[k++] = hexDigits[byte0 >>> 4 & 0xf];
                buf[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(buf);
        } catch (Exception e) {
            // TODO: handle exception
            return null;
        }
    }
    @org.junit.Test
    public void testSort(){
        int []arr=new int[]{2,1,3};
        Arrays.sort(arr);
        for (int i:arr){
            System.out.print(i+":");
        }
        String[]strArry=new String[]{"cbc","aba","bac"};
        Arrays.sort(strArry);
        System.out.println();
        for (String s:strArry){
            System.out.print(s+":");
        }
    }
    @org.junit.Test
    public void testEncode() throws UnsupportedEncodingException {
        System.out.println(URLEncoder.encode("http://b7eb9404.ngrok.io/webpub/index","UTF-8"));
        System.out.println(System.currentTimeMillis());
    }
    @org.junit.Test
    public void testFile(){
        InputStream inputStream=this.getClass().getClassLoader().getResourceAsStream("dir");
        if(inputStream==null){
            File file=new File("./src/main/resources/dir");
            file.mkdir();
        }
    }
}
