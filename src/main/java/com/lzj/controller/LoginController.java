package com.lzj.controller;

import com.lzj.dao.UserDao;
import com.lzj.domain.User;
import com.lzj.utils.SHA1;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import sun.security.provider.SHA;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Created by li on 17-8-7.
 */
@Controller

public class LoginController {
    @Autowired
    private UserDao userDao;
    @RequestMapping(value = "/loginAct",method = RequestMethod.POST)
    public String loginAct(HttpServletRequest request, HttpSession session){
        String userName=request.getParameter("userName");
        String password=request.getParameter("password");
        User user=userDao.findByUserNameAndPassword(userName,password);
        if (user!=null){
            session.setAttribute("user",user);
            return "index";
        }
        return "login";
    }
    @RequestMapping("/login")
    public String loginHTML(){
        return "login";
    }


}
