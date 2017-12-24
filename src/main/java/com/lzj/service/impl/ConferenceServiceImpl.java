package com.lzj.service.impl;

import com.lzj.dao.dto.FunctionDto;
import com.lzj.domain.Account;
import com.lzj.domain.Function;
import com.lzj.service.FunctionService;
import org.springframework.stereotype.Service;

@Service
public class ConferenceServiceImpl implements FunctionService{
    @Override
    public boolean hasFunctions(Account currentAccount, FunctionDto dto, Function function) {
        return false;
    }
}
