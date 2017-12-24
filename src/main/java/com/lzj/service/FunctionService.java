package com.lzj.service;

import com.lzj.dao.dto.FunctionDto;
import com.lzj.domain.Account;
import com.lzj.domain.Function;

public interface FunctionService {

    /**
     *查询是否有此权限
     * @param currentAccount
     * @param dto
     * @param functions
     * @param type @see FunctionTypeEnum
     * @return
     */
     boolean hasFunctions(Account currentAccount, FunctionDto dto, Function function);
}
