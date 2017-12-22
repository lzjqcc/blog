package com.lzj.domain;

import java.io.Serializable;

public class Group extends BaseEntity implements Serializable{
    private String groupName;
    private String currentAccountId;

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getCurrentAccountId() {
        return currentAccountId;
    }

    public void setCurrentAccountId(String currentAccountId) {
        this.currentAccountId = currentAccountId;
    }
}
