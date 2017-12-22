package com.lzj.domain;

import java.io.Serializable;

public class DiscussionGroup extends BaseEntity implements Serializable {
    private String discussionGroupName;
    private Integer currentAccountId;

    public String getDiscussionGroupName() {
        return discussionGroupName;
    }

    public void setDiscussionGroupName(String discussionGroupName) {
        this.discussionGroupName = discussionGroupName;
    }

    public Integer getCurrentAccountId() {
        return currentAccountId;
    }

    public void setCurrentAccountId(Integer currentAccountId) {
        this.currentAccountId = currentAccountId;
    }
}
