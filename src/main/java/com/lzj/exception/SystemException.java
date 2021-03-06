package com.lzj.exception;

import java.util.Date;

//系统异常
public class SystemException extends RuntimeException{
    private Integer code;
    private String message;
    private String className;
    private String method;
    private String args;
    private Exception e;
    private String exceptionString;
    private Date createTime;

    public String getArgs() {
        return args;
    }

    public void setArgs(String args) {
        this.args = args;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public SystemException(){

    }
    public SystemException(Integer code,String message){
        this.code=code;
        this.message=message;
    }
    public SystemException(Integer code,String message,Exception e){
        this.code=code;
        this.message=message;
        this.e=e;
    }
    public SystemException(Integer code,String message,String className,String method,String exceptionString){
        this.code=code;
        this.message=message;
        this.className=className;
        this.method=method;
        this.exceptionString=exceptionString;
    }
    public SystemException(Integer code, String message,String className,String method,String args , String exceptionString) {
        this.code = code;
        this.message= message;
        this.className = className;
        this.method = method;
        this.args = args;
    }
    public String getExceptionString() {
        return exceptionString;
    }

    public void setExceptionString(String exceptionString) {
        this.exceptionString = exceptionString;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Exception getE() {
        return e;
    }

    public void setE(Exception e) {
        this.e = e;
    }

    @Override
    public String toString() {
        return "SystemException{" +
                "code=" + code +
                ", message='" + message + '\'' +
                ", e=" + e +
                '}';
    }
}
