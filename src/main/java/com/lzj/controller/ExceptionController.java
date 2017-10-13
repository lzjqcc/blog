package com.lzj.controller;

import com.lzj.dao.ExceptionDao;
import com.lzj.domain.Exception;
import com.lzj.exception.BusinessException;
import com.lzj.exception.ExceptionEntity;
import com.lzj.exception.SystemException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MultipartException;

/**
 * 业务异常直接显示在前端，系统异常先保存然后再返回前端
 */
@ControllerAdvice(basePackages = "com.lzj.controller")
public class ExceptionController {
    @Autowired
    private ExceptionDao exceptionDao;
    @ExceptionHandler(value = BusinessException.class)
    public ResponseEntity getExceptionEntity(BusinessException e){
        ExceptionEntity exceptionEntity=new ExceptionEntity(e.getCode(),e.getMessage());
        ResponseEntity entity=new ResponseEntity(exceptionEntity,HttpStatus.OK);
        return entity;
    }
    @ExceptionHandler(value = SystemException.class)
    public ResponseEntity getSystemException(SystemException sytemException){
        Exception exception=new Exception();
        BeanUtils.copyProperties(sytemException,exception);
        exceptionDao.insertException(exception);
        return new ResponseEntity(exception,HttpStatus.OK);
    }
}

