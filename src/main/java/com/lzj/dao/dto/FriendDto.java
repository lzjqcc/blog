package com.lzj.dao.dto;

import javax.validation.constraints.NotNull;
import java.util.Date;

public class FriendDto {
    private Date createTime;
    private Date updateTime;
    private Integer currentAccountId;
    private Integer friendId;
    private Integer groupId;
    // @see FriendStatusEnum
    private Integer status;
    private Boolean isDefriend;
    // true 特别关注
    private Boolean specialAttention;
    private String friendName;
    private String personalSignature;
    private Integer messageId;

    public Integer getMessageId() {
        return messageId;
    }

    public void setMessageId(Integer messageId) {
        this.messageId = messageId;
    }

    public Boolean getSpecialAttention() {
        return specialAttention;
    }
    public String getFriendName() {
        return friendName;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getPersonalSignature() {
        return personalSignature;
    }

    public void setPersonalSignature(String personalSignature) {
        this.personalSignature = personalSignature;
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
