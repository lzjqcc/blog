package com.lzj.controller;

import com.lzj.exception.BusinessException;
import com.lzj.exception.ExceptionEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MultipartException;

@ControllerAdvice(basePackages = "com.lzj.controller")
public class ExceptionController {
    @ExceptionHandler(value = BusinessException.class)
    public ResponseEntity getExceptionEntity(BusinessException e){
        ExceptionEntity exceptionEntity=new ExceptionEntity(e.getCode(),e.getMessage());
        ResponseEntity entity=new ResponseEntity(exceptionEntity,HttpStatus.OK);
        return entity;
    }
}

