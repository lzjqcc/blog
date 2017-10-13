package com.lzj.dao;

import com.lzj.domain.Exception;
import org.springframework.stereotype.Repository;

@Repository("exceptionDao")
public interface ExceptionDao {
    void insertException(Exception e);
}
