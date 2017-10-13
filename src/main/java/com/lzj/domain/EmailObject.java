package com.lzj.domain;

public  class EmailObject{
    String userName;//邮箱用户名
    String password;//用户密码
    String host;//邮箱地址
    String sendTo;//发给谁
    String defaultEncoding;//默认编码
    String content;//内容
    String subject;//标题
    public EmailObject(){

    }
    public EmailObject(String userName, String password, String host, String sendTo, String defaultEncoding, String content, String subject) {
        this.userName = userName;
        this.password = password;
        this.host = host;
        this.sendTo = sendTo;
        this.defaultEncoding = defaultEncoding;
        this.content = content;
        this.subject = subject;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getSendTo() {
        return sendTo;
    }

    public void setSendTo(String sendTo) {
        this.sendTo = sendTo;
    }

    public String getDefaultEncoding() {
        return defaultEncoding;
    }

    public void setDefaultEncoding(String defaultEncoding) {
        this.defaultEncoding = defaultEncoding;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }
}