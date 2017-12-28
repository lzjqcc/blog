package com.lzj.dao.dto;

import javax.validation.constraints.NotNull;

public class GroupDto {
    private String groupName;
    @NotNull
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
