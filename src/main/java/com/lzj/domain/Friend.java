package com.lzj.domain;

import com.lzj.annotation.EnableRelationTable;
import com.sun.org.apache.xpath.internal.operations.Bool;

import java.io.Serializable;
import java.util.List;

public class Friend extends BaseEntity implements Serializable {
    private Integer currentAccountId;
    private Integer friendId;
    private Boolean specialAttention;
    private Boolean isDefriend;
    private Boolean isAgree;
    private List<Function> list = null;

    @EnableRelationTable(relationTableName = "tb_friend_relation",value = "friend_id")
    @Override
    public Integer getId() {
        return id;
    }
    @EnableRelationTable(relationTableName = "tb_friend_relation",value = "function_id",keyRow = true)
    public List<Function> getList() {
        return list;
    }

    public void setList(List<Function> list) {
        this.list = list;
    }
    @EnableRelationTable(relationTableName = "tb_friend_relation",value = "current_account_id")
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

    public Boolean getIsDefriend() {
        return isDefriend;
    }

    public void setIsDefriend(Boolean defriend) {
        isDefriend = defriend;
    }

    public Boolean getIsAgree() {
        return isAgree;
    }

    public void setIsAgree(Boolean agree) {
        isAgree = agree;
    }
}
