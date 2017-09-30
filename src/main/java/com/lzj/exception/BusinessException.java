package com.lzj.exception;
//业务异常
public class BusinessException extends RuntimeException {
    private Integer code;
    private String message;
    private Exception e;
    public BusinessException(){

    }
    public BusinessException(Integer code,String message){
        this.code=code;
        this.message=message;
    }
    public BusinessException(Integer code,String message,Exception e){
        this.code=code;
        this.message=message;
        this.e=e;
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
