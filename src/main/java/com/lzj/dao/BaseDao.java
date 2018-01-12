package com.lzj.dao;

import com.lzj.domain.Function;

import java.util.List;

public interface BaseDao {
    List<Function> findFuncation(Integer currentAccountId, Integer entityId);
}
