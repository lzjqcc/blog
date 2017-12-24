package com.lzj.service.impl;

import com.lzj.dao.AccountDao;
import com.lzj.dao.dto.AccountDto;
import com.lzj.domain.Account;
import com.lzj.service.AccountService;
import com.lzj.utils.ComentUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;

@Service
@Cacheable(value = "users")
public class AccountServiceImpl implements AccountService {
    @Autowired
    AccountDao accountDao;


    @Override
    public Boolean insertUser(AccountDto dto, HttpSession session) {

        String icon=ComentUtils.ICON_DIR.substring(ComentUtils.ICON_DIR.indexOf("static"));
        dto.setHeadIcon(icon+"/default/default.jpg");
        Account account = new Account();
        BeanUtils.copyProperties(dto,account);
        int count = accountDao.insertAccount(account);
        if (count == 0) {
            return false;
        }
        if (session!=null){
            session.setAttribute("user",dto);
        }
        return true;
    }
    @Override
    public Boolean exitsUser(String name,String email){
        AccountDto dto = new AccountDto();
        dto.setEmail(email);
        if ( accountDao.findByDto(dto)!=null){
            return true;
        }
        return false;


    }
    @Override
    public void updateUser(AccountDto user) {

        accountDao.updateUser(user);
    }

    @Override
    public Account findByDto(AccountDto dto) {
      return accountDao.findByDto(dto);
    }
}
