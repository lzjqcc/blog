package com.lzj.service.impl;

import com.google.common.collect.Lists;
import com.lzj.VO.PageVO;
import com.lzj.constant.AuthorityEnum;
import com.lzj.dao.AccountDao;
import com.lzj.dao.FunctionDao;
import com.lzj.dao.dto.AccountDto;
import com.lzj.dao.dto.GroupDto;
import com.lzj.domain.Account;
import com.lzj.domain.Friend;
import com.lzj.domain.Function;
import com.lzj.domain.Page;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Cacheable(value = "users")
public class AccountServiceImpl implements AccountService {
    @Autowired
    AccountDao accountDao;
    @Autowired
    FunctionDao functionDao;
    @Autowired
    FriendService friendService;
    @Autowired
    GroupService groupService;

    @Override
    public Boolean insertUser(AccountDto dto) {
        Account account = new Account();
        dto.setHeadIcon(account.getHeadIcon());
        BeanUtils.copyProperties(dto,account);
        AccountDto query = new AccountDto();
        query.setEmail(dto.getEmail());
        Account queryAccount = accountDao.findByDto(query);
        if (queryAccount != null) {
            return false;
        }
         accountDao.insertAccount(account);
        if (account.getId() == null) {
            return false;
        }
        AccountToken token = new AccountToken(dto.getEmail(),dto.getPassword(), null);
        token.setAccount(account);
        SecurityContextHolder.getContext().setAuthentication(token);
        initGroup(account.getId());
        return true;
    }
    private void initGroup(Integer currentAccountId) {
        List<String> list = Lists.newArrayList("我的好友", "我的家人", "陌生人");
        for (String name : list) {
            GroupDto groupDto = new GroupDto();
            groupDto.setCurrentAccountId(currentAccountId);
            groupDto.setGroupName(name);
            groupService.insertGroup(groupDto);
        }

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
    public PageVO<List<AccountDto>> searchAccount(String searchKey, Page page) {
        PageVO<List<AccountDto>> pageVO = new PageVO<>();
        List<Account> list = accountDao.searchAccount(searchKey,ComentUtils.getCurrentAccount().getId(), page);
        List<AccountDto> accountDtos = new ArrayList<>();
        for (Account account : list) {
            accountDtos.add(buildAccountDto(account));
        }
        pageVO.setResult(accountDtos);
        pageVO.setPage(page);
        return pageVO;
    }
    private AccountDto buildAccountDto(Account account) {
        AccountDto dto = new AccountDto();
        BeanUtils.copyProperties(account, dto);
        return dto;
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
