package com.lzj.service;

import com.lzj.dao.dto.AccountDto;
import com.lzj.domain.Account;

import javax.servlet.http.HttpSession;

public interface AccountService {
    /**
     * 用户注册用户名，邮箱要唯一
     * @return 1表示用户名存在，2表示邮箱已存在，3表示注册成功
     */
    Boolean insertUser(AccountDto dto);
    void updateUser(AccountDto user );

    Account findByDto(AccountDto dto);
    /**
     * 检查用户是否存在
     * @param name
     * @param email
     * @return true表示用户存在，false表示不存在
     */
    Boolean exitsUser(String name,String email);

}
