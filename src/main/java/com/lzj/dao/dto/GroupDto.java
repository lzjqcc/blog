package com.lzj.dao.dto;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

public class GroupDto implements Serializable{
    private static final long serialVersionUID = 7101682645707919509L;
    private Integer id;
    private String groupName;
    private Integer currentAccountId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public Integer getCurrentAccountId() {
        return currentAccountId;
    }

    public void setCurrentAccountId(Integer currentAccountId) {
        this.currentAccountId = currentAccountId;
    }
}
