package com.lzj.domain;

import java.io.Serializable;

public class GroupChat extends BaseEntity implements Serializable {
    private Integer currentAccountId;
    private Integer discussionGroupId;
    private Integer speechId;
    private String message;

    public Integer getCurrentAccountId() {
        return currentAccountId;
    }

    public void setCurrentAccountId(Integer currentAccountId) {
        this.currentAccountId = currentAccountId;
    }

    public Integer getDiscussionGroupId() {
        return discussionGroupId;
    }

    public void setDiscussionGroupId(Integer discussionGroupId) {
        this.discussionGroupId = discussionGroupId;
    }

    public Integer getSpeechId() {
        return speechId;
    }

    public void setSpeechId(Integer speechId) {
        this.speechId = speechId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
