package com.lzj.domain;

import java.io.Serializable;

public class DiscussionGroupMember extends BaseEntity implements Serializable {
    private Integer discussionGroupId;
    private Integer memberId;
    private Integer currentAccountId;

    public Integer getDiscussionGroupId() {
        return discussionGroupId;
    }

    public void setDiscussionGroupId(Integer discussionGroupId) {
        this.discussionGroupId = discussionGroupId;
    }

    public Integer getMemberId() {
        return memberId;
    }

    public void setMemberId(Integer memberId) {
        this.memberId = memberId;
    }

    public Integer getCurrentAccountId() {
        return currentAccountId;
    }

    public void setCurrentAccountId(Integer currentAccountId) {
        this.currentAccountId = currentAccountId;
    }
}
