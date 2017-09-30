package com.lzj.service;

import com.lzj.domain.User;

import javax.servlet.http.HttpSession;

public interface UserService {
    /**
     * 用户注册用户名，邮箱要唯一
     * @param name
     * @param email
     * @param password
     * @return 1表示用户名存在，2表示邮箱已存在，3表示注册成功
     */
    public Integer insertUser(String name, String email, String password, HttpSession session);
    void updateUser(User user );
    User findByEmailOrNameAndPassword(String nameOrEmail,String password);
    User findById(Integer id);

    /**
     * 检查用户是否存在
     * @param name
     * @param email
     * @return1表示用户名存在，2表示邮箱已存在，3表示不存在
     */
    Integer exitsUser(String name,String email);

}
