package com.lzj.domain;

import com.sun.org.apache.xpath.internal.operations.Bool;

import java.io.Serializable;

public class Friend extends BaseEntity implements Serializable {
    private Integer currentAccountId;
    private Integer friendId;
    private Boolean specialAttention;
    private Boolean isDefriend;
    private Boolean isDelete;
    private Boolean isAgree;

    public Integer getCurrentAccountId() {
        return currentAccountId;
    }

    public void setCurrentAccountId(Integer currentAccountId) {
        this.currentAccountId = currentAccountId;
    }

    public Integer getFriendId() {
        return friendId;
    }

    public void setFriendId(Integer friendId) {
        this.friendId = friendId;
    }

    public Boolean getSpecialAttention() {
        return specialAttention;
    }

    public void setSpecialAttention(Boolean specialAttention) {
        this.specialAttention = specialAttention;
    }

    public Boolean getDefriend() {
        return isDefriend;
    }

    public void setDefriend(Boolean defriend) {
        isDefriend = defriend;
    }

    public Boolean getDelete() {
        return isDelete;
    }

    public void setDelete(Boolean delete) {
        isDelete = delete;
    }

    public Boolean getAgree() {
        return isAgree;
    }

    public void setAgree(Boolean agree) {
        isAgree = agree;
    }
}
