package com.lzj.service.impl;

import com.lzj.dao.UserDao;
import com.lzj.domain.User;
import com.lzj.service.UserService;
import com.lzj.utils.ComentUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;

@Service
@Cacheable(value = "users")
public class UserServiceImpl implements UserService{
    @Autowired
    UserDao userDao;


    @Override
    public Integer insertUser(String name, String email, String password, HttpSession session) {
        if (exitsUser(name,email)==1){
            return 1;
        }
        if (exitsUser(name,email)==2){
            return 2;
        }
        String icon=ComentUtils.ICON_DIR.substring(ComentUtils.ICON_DIR.indexOf("static"));
        User user=new User();
        user.setPassword(password);
        user.setUserName(name);
        user.setEmail(email);
        user.setRole(User.Role.ROLE_USER);
        user.setIcon(icon+"/default/default.jpg");
        userDao.insertUser(user);
        if (session!=null){
            session.setAttribute("user",user);
        }
        return 3;
    }
    public Integer exitsUser(String name,String email){
       ;
        if ( userDao.findByNameOrEmail(name,null)!=null){
            return 1;
        }
        if (userDao.findByNameOrEmail(null,email)!=null){
            return 2;
        }
        return 3;


    }
    @Override
    public void updateUser(User user) {

        userDao.updateUser(user);
    }

    @Override
    public User findByEmailOrNameAndPassword(String nameOrEmail,String password) {
        User user=new User();
        if (nameOrEmail.contains("@")){
            user.setEmail(nameOrEmail);
        }else {
            user.setUserName(nameOrEmail);
        }
        user.setPassword(password);
        return userDao.findByEmailOrNameAndPassword(user);
    }
    @Override
    public User findById(Integer id) {
        return userDao.findById(id);
    }
}
