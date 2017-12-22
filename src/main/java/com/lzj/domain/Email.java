package com.lzj.domain;

import com.sun.org.apache.xpath.internal.operations.Bool;

import java.io.Serializable;

public class Email extends BaseEntity implements Serializable {
    private Integer currentAccountId;
    private Integer receiveAccountId;
    private Boolean isDelete;
    private String content;

    public Integer getCurrentAccountId() {
        return currentAccountId;
    }

    public void setCurrentAccountId(Integer currentAccountId) {
        this.currentAccountId = currentAccountId;
    }

    public Integer getReceiveAccountId() {
        return receiveAccountId;
    }

    public void setReceiveAccountId(Integer receiveAccountId) {
        this.receiveAccountId = receiveAccountId;
    }

    public Boolean getDelete() {
        return isDelete;
    }

    public void setDelete(Boolean delete) {
        isDelete = delete;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
