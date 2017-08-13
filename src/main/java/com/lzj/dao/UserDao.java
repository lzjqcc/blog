package com.lzj.dao;

import com.lzj.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * Created by li on 17-8-6.
 */
public interface UserDao extends JpaRepository<User,Integer>{
    public User findByUserName(String userName);
    public User findByUserNameAndPassword(String userName,String pasword);
}
