package com.lzj.dao;

import com.lzj.domain.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * Created by li on 17-8-6.
 */
@Repository("userDao")
public interface UserDao{
    public User findByUserName(String userName);
    public User findByUserNameAndPassword(String userName,String pasword);
    public Integer insertUser(User user);
    void updateUser(User user);
    User findByEmailOrNameAndPassword(User user);
    User findById(Integer id);

    /**name==null根据email查询
     * email==null 根据name查询
     * email=null&&name=null 查询所有
     * @param name
     * @param email
     * @return
     */
    User findByNameOrEmail(@Param("name") String name,
                           @Param("email") String email);
}
