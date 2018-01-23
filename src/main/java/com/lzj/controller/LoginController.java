package com.lzj.controller;

import com.lzj.dao.MessageDao;
import com.lzj.dao.dto.AccountDto;
import com.lzj.dao.dto.MessageDto;
import com.lzj.domain.Account;
import com.lzj.domain.EmailObject;
import com.lzj.domain.MessageInfo;
import com.lzj.service.AccountService;
import com.lzj.service.impl.WebScoketService;
import com.lzj.utils.ComentUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.simp.user.SimpUserRegistry;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
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
    private AccountService accountService;
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
        备注：登录操作已经整合到SpringSecurity中
     * @param session
     * @return
     */
    @Deprecated
    @RequestMapping(value = "/loginAct",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> loginAct(@RequestBody AccountDto dto,
                           HttpSession session){
        Account account= accountService.findByDto(dto);
        Map<String, Object> result = new HashMap<>();
        if (account!=null){
            session.setAttribute("user",account);
            //sendMessage(account);
            result.put("result", "success");
            result.put("currentUser", account);
            return result;
        }
        result.put("result", "fail");
        return result;
    }
    private void sendMessage(Account account) {
        MessageDto messageDto = new MessageDto();
        messageDto.setToAccountId(account.getId());
        messageDto.setType(false);
        List<MessageInfo> list=messageDao.findMessagesByDto(messageDto);
        if (list != null && list.size() > 0){
            webScoketService.sendNotReadMessageToUser(list);
        }
    }

    @ResponseBody
    @GetMapping("/get")
    public Account get(HttpSession session) {
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();
        return (Account) session.getAttribute("user");
    }
    /**
     * 用户注册成功后将用户名密码发送给用户邮箱
     * @return
     */
    @RequestMapping("/registerAct")
    @ResponseBody
    public Map<String,String> registerAct(@RequestBody AccountDto user){
        Map<String, String> result = new HashMap<>();
        if (user.getEmail()!=null && !user.getEmail().contains("@") && user.getEmail().lastIndexOf(".com")==-1){
            result.put("result", "邮箱格式错误");
            return result;
        }
        if (!accountService.insertUser(user)){
            result.put("result", "用户名存在");
            return result;
        }
        EmailObject object=new EmailObject();
        object.setSendTo(user.getEmail());
        object.setDefaultEncoding("UTF-8");
        object.setSubject("用户注册");
        object.setContent("");
        ComentUtils.sendEmail(object);
        result.put("result", "注册成功");
        return result;
    }
}
