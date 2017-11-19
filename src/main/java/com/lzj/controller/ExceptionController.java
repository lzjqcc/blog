/*
package com.lzj.controller;

import com.lzj.dao.ExceptionDao;
import com.lzj.domain.Exception;
import com.lzj.domain.User;
import com.lzj.exception.BusinessException;
import com.lzj.exception.ExceptionEntity;
import com.lzj.exception.SystemException;
import com.lzj.service.impl.WebScoketService;
import com.lzj.utils.ComentUtils;
import com.lzj.utils.LoginUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MultipartException;

import javax.servlet.http.HttpSession;

*/
/**
 * 业务异常直接显示在前端,系统异常保存
 *//*

@ControllerAdvice(basePackages = "com.lzj.controller")
public class ExceptionController {
    @Autowired
    private ExceptionDao exceptionDao;
    @Autowired
    private WebScoketService webScoketService;
    @ExceptionHandler(value = java.lang.Exception.class)
    public ResponseEntity getExceptionEntity(java.lang.Exception ex){
        if (ex instanceof BusinessException){
            BusinessException e= (BusinessException) ex;
            ExceptionEntity exceptionEntity=new ExceptionEntity(e.getCode(),e.getMessage());
            ResponseEntity entity=new ResponseEntity(exceptionEntity,HttpStatus.OK);
            return entity;
        }else if (ex instanceof SystemException){
            SystemException systemException= (SystemException) ex;
            Exception exception=new Exception();
            BeanUtils.copyProperties(systemException,exception);
            exceptionDao.insertException(exception);
            ComentUtils.saveSystemException(systemException);
            return new ResponseEntity(HttpStatus.OK);
        }
        Exception systemException = new Exception();
        systemException.setExceptionString(ex.toString());
        exceptionDao.insertException(systemException);
        return new ResponseEntity(HttpStatus.OK);

    }

}

*/
