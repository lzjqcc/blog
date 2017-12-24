package com.lzj.domain;

import java.io.Serializable;

public class Chat extends BaseEntity implements Serializable {
    private Integer currentAccountId;
    private Integer fromAccountId;
    private Integer toAccountId;
    private String message;
    private Boolean isDelete;

    public Integer getCurrentAccountId() {
        return currentAccountId;
    }

    public Chat setCurrentAccountId(Integer currentAccountId) {
        this.currentAccountId = currentAccountId;
        return this;
    }

    public Integer getFromAccountId() {
        return fromAccountId;
    }

    public Chat setFromAccountId(Integer fromAccountId) {
        this.fromAccountId = fromAccountId;
        return this;
    }

    public Integer getToAccountId() {
        return toAccountId;
    }

    public Chat setToAccountId(Integer toAccountId) {
        this.toAccountId = toAccountId;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public Chat setMessage(String message) {
        this.message = message;
        return this;
    }

    public Boolean getIsDelete() {
        return isDelete;
    }

    public Chat setIsDelete(Boolean isDelete) {
        this.isDelete = isDelete;
        return this;
    }
}
