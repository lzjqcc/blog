package com.lzj.dao.dto;

import javax.validation.constraints.NotNull;

public class FriendDto {
    private Integer currentAccountId;
    @NotNull
    private Integer friendId;
    private Integer groupId;
    private Integer status;
    private Boolean isDefriend;
    // true 特别关注
    private Boolean specialAttention;
    private String friendName;
    public Boolean getSpecialAttention() {
        return specialAttention;
    }

    public String getFriendName() {
        return friendName;
    }

    public void setFriendName(String friendName) {
        this.friendName = friendName;
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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }
}
