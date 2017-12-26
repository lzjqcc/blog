package com.lzj.domain;

import com.lzj.annotation.EnableRelationTable;

import java.io.Serializable;
import java.util.List;

public class Friend extends BaseEntity implements Serializable {
    private Integer currentAccountId;
    private Integer friendId;
    private Boolean specialAttention;
    private Boolean isDefriend;
    private Boolean isAgree;
    private List<Integer> functionList = null;

    @EnableRelationTable(relationTableName = "tb_friend_function",value = "friend_id")
    @Override
    public Integer getId() {
        return id;
    }
    @EnableRelationTable(relationTableName = "tb_friend_function",value = "function_id",keyRow = true)
    public List<Integer> getFunctionList() {
        return functionList;
    }

    public void setFunctionList(List<Integer> functionList) {
        this.functionList = functionList;
    }
    @EnableRelationTable(relationTableName = "tb_friend_function",value = "current_account_id")
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
