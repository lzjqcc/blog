package com.lzj.controller;

import com.lzj.dao.MessageDao;
import com.lzj.domain.EmailObject;
import com.lzj.domain.MessageInfo;
import com.lzj.domain.User;
import com.lzj.service.UserService;
import com.lzj.service.impl.WebScoketService;
import com.lzj.utils.ComentUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.simp.user.SimpUserRegistry;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**用户登录，注册
 * Created by li on 17-8-7.
 */
@Controller
public class LoginController {
    @Autowired
    SimpUserRegistry simpUserRegistry;
    @Autowired
    private UserService userService;
    @Value("${userName}")
    String userName;
    @Value("${password}")
    String password;
    @Value("${host}")
    String host;
    @Autowired
    MessageDao messageDao;

    @Autowired
    WebScoketService webScoketService;
    /**
     * 登录成功后发送未读信息
     * @param nameOrEmail
     * @param password
     * @param session
     * @return
     */
    @RequestMapping(value = "/loginAct",method = RequestMethod.GET)
    @ResponseBody
    public String loginAct(@RequestParam("nameOrEmail")String nameOrEmail,
                           @RequestParam("password")String password,
                           HttpSession session){
        User user=userService.findByEmailOrNameAndPassword(nameOrEmail,password);
        if (user!=null){
            session.setAttribute("user",user);
            List<MessageInfo> list=messageDao.getMessages(ComentUtils.buildMessageCondition(user.getId(),false,null));
            webScoketService.sendNotReadMessageToUser(list);
            return "success";
        }
        return "fail";
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
