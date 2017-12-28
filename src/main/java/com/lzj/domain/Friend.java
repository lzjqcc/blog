package com.lzj.domain;

import com.lzj.annotation.EnableRelationTable;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

public class Friend extends BaseEntity implements Serializable {
    private Integer currentAccountId;
    private Integer friendId;
    // true 特别关注
    private Boolean specialAttention;
    // true 拉黑 false
    private Boolean isDefriend;
    // @link FriendStatusEnum
    private Integer status;
    private String friendName;
    @NotNull
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
        return this.isDefriend;
    }

    public void setIsDefriend(Boolean defriend) {
        this.isDefriend = defriend;
    }

    public Integer getStatus() {
        return this.status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getFriendName() {
        return friendName;
    }

    public void setFriendName(String friendName) {
        this.friendName = friendName;
    }
}
