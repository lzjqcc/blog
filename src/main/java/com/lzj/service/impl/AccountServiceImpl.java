package com.lzj.service.impl;

import com.lzj.constant.AuthorityEnum;
import com.lzj.dao.AccountDao;
import com.lzj.dao.FunctionDao;
import com.lzj.dao.dto.AccountDto;
import com.lzj.domain.Account;
import com.lzj.domain.Function;
import com.lzj.security.AccountToken;
import com.lzj.service.AccountService;
import com.lzj.utils.ComentUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpSession;

@Service
@Cacheable(value = "users")
public class AccountServiceImpl implements AccountService {
    @Autowired
    AccountDao accountDao;
    @Autowired
    FunctionDao functionDao;


    @Override
    public Boolean insertUser(AccountDto dto) {

        String icon=ComentUtils.ICON_DIR.substring(ComentUtils.ICON_DIR.indexOf("static"));
        dto.setHeadIcon(icon+"/default/default.jpg");
        Account account = new Account();
        BeanUtils.copyProperties(dto,account);
         accountDao.insertAccount(account);
        if (account.getId() == null) {
            return false;
        }
        AccountToken token = new AccountToken(dto.getUserName(),dto.getPassword(), null);
        SecurityContextHolder.getContext().setAuthentication(token);
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
