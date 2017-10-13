package com.lzj.controller;

import com.lzj.dao.UserDao;
import com.lzj.domain.EmailObject;
import com.lzj.domain.User;
import com.lzj.service.UserService;
import com.lzj.utils.ComentUtils;
import com.lzj.utils.SHA1;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import sun.security.provider.SHA;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**用户登录，注册
 * Created by li on 17-8-7.
 */
@Controller
public class LoginController {
    @Autowired
    private UserService userService;
    @Value("${userName}")
    String userName;
    @Value("${password}")
    String password;
    @Value("${host}")
    String host;
    @RequestMapping(value = "/loginAct",method = RequestMethod.GET)
    @ResponseBody
    public String loginAct(@RequestParam("nameOrEmail")String nameOrEmail,
                           @RequestParam("password")String password,
                           HttpSession session){
        User user=userService.findByEmailOrNameAndPassword(nameOrEmail,password);
        if (user!=null){
            session.setAttribute("user",user);
            return "success";
        }
        return "fail";
    }

    @RequestMapping("/loginPage")
    public String loginHTML(){
        return "login";
    }
    @RequestMapping("/registerPage")
    public String registerHTML(){
        return "register";
    }
    @RequestMapping("/indexPage")
    public String index(){
        return "index";
    }
    /**
     * 用户注册成功后将用户名密码发送给用户邮箱
     * @param name
     * @param password
     * @param email
     * @return
     */
    @RequestMapping("/registerAct")
    @ResponseBody
    public String registerAct(@RequestParam("name")String name,
                              @RequestParam("password")String password,
                              @RequestParam("email")String email,HttpSession session){
        if (email!=null && !email.contains("@") && email.lastIndexOf(".com")==-1){
            return "邮箱格式错误";
        }
        Integer i=userService.insertUser(name,email,password,session);
        if (i==1){
            return "用户名存在";
        }
        if (i==2){
            return "邮箱已存在";
        }
        EmailObject object=new EmailObject();
        object.setUserName(userName);
        object.setHost(host);
        object.setPassword(password);
        object.setSendTo(email);
        object.setDefaultEncoding("UTF-8");
        object.setSubject("用户注册");
        object.setContent("");
        ComentUtils.sendEmail(object);
        return "注册成功";
    }

}
