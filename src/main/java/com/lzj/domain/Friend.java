package com.lzj.domain;

import com.lzj.annotation.EnableRelationTable;
import com.sun.org.apache.xpath.internal.operations.Bool;

import java.io.Serializable;
import java.util.List;

public class Friend extends BaseEntity implements Serializable {
    @EnableRelationTable(relationTableName = "tb_friend_relation",value = {"friend_id"})
    private Integer id;
    @EnableRelationTable(relationTableName = "tb_friend_relation",value = {"current_account_id"})
    private Integer currentAccountId;
    private Integer friendId;
    private Boolean specialAttention;
    private Boolean isDefriend;
    private Boolean isDelete;
    private Boolean isAgree;
    @EnableRelationTable(relationTableName = "tb_friend_relation",value = {"function_id"},keyRow = true)
    private List<Function> list = null;

    public List<Function> getList() {
        return list;
    }

    public void setList(List<Function> list) {
        this.list = list;
    }

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
