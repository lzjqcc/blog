package com.lzj.controller;

import com.lzj.dao.MessageDao;
import com.lzj.dao.dto.AccountDto;
import com.lzj.domain.Account;
import com.lzj.domain.EmailObject;
import com.lzj.domain.MessageInfo;
import com.lzj.service.AccountService;
import com.lzj.service.impl.WebScoketService;
import com.lzj.utils.ComentUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.simp.user.SimpUserRegistry;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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
    private AccountService userService;
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

     * @param session
     * @return
     */
    @RequestMapping(value = "/loginAct",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,String> loginAct(@RequestBody AccountDto dto,
                           HttpSession session){
        Account user=userService.findByDto(dto);
        Map<String, String> result = new HashMap<>();
        if (user!=null){
            session.setAttribute("user",user);
            List<MessageInfo> list=messageDao.getMessages(ComentUtils.buildMessageCondition(user.getId(),false,null));
            webScoketService.sendNotReadMessageToUser(list);
            result.put("result", "success");
            return result;
        }
        result.put("result", "fail");
        return result;
    }

    /**
     * 用户注册成功后将用户名密码发送给用户邮箱
     * @return
     */
    @RequestMapping("/registerAct")
    @ResponseBody
    public Map<String,String> registerAct(@RequestBody AccountDto user, HttpSession session){
        Map<String, String> result = new HashMap<>();
        if (user.getEmail()!=null && !user.getEmail().contains("@") && user.getEmail().lastIndexOf(".com")==-1){
            result.put("result", "邮箱格式错误");
            return result;
        }
        if (!userService.insertUser(user,session)){
            result.put("result", "用户名存在");
            return result;
        }
        EmailObject object=new EmailObject();
        object.setUserName(userName);
        object.setHost(host);
        object.setPassword(password);
        object.setSendTo(user.getEmail());
        object.setDefaultEncoding("UTF-8");
        object.setSubject("用户注册");
        object.setContent("");
        ComentUtils.sendEmail(object);
        result.put("result", "注册成功");
        return result;
    }

}
