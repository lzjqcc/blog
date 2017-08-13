package com.lzj.controller;

import com.lzj.service.WebpubService;
import com.lzj.utils.SHA1;
import com.lzj.utils.WebPubUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.Map;

/**
 * Created by li on 17-8-13.
 */
@Controller
@RequestMapping("webpub")
//secret:111cb4031fe130b5fde0cf904bc4fc05
//appid:wx8dc1cca3fcc9f474
public class WebPubController {
    @Autowired
    private WebpubService webpubService;
    /**
     * 验签：微信发送过来的的
     * @param request
     * @return
     */
    @RequestMapping("verify")
    @ResponseBody
    public String verify(HttpServletRequest request){
        //System.out.println(request.getParameter("signature"));
        String signature=request.getParameter("signature");
        String timestamp=request.getParameter("timestamp");
        String nonce=request.getParameter("nonce");
        String token="lzjqcc";
        String echostr=request.getParameter("echostr");
        String verify= SHA1.verify(nonce,timestamp,token);
        if (verify.equals(signature)){
            return echostr;
        }
        return "fail";
    }
    @RequestMapping("getUserInfo")
    public String getUserInfo(){
        return "index";
    }
    @RequestMapping("index")
    public String index(HttpServletRequest request){
        String code=request.getParameter("code");
        System.out.println(request.getRequestURI());
        Map map=webpubService.requestUserInfo(code);
        request.setAttribute("user",map);
        System.out.println(map);
        return "index";
    }
    @RequestMapping("code")

    public String code() throws UnsupportedEncodingException {

        return "redirect:"+WebPubUtils.getCodeURL();
    }
    @RequestMapping("getStaticAccessToken")
    @ResponseBody
    public Map getAccessToken(){
        return webpubService.requestStaticAccessToken();
    }
    @RequestMapping("first")
    public String first(){
        return "first";
    }
}
