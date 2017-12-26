package com.lzj.domain;

import java.io.Serializable;

/**
 * 好友分组
 */
public class GroupFunction extends BaseEntity implements Serializable {
    private Integer groupId;
    private Integer functionId;
    private Integer currentAccountId;

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    public Integer getFunctionId() {
        return functionId;
    }

    public void setFunctionId(Integer functionId) {
        this.functionId = functionId;
    }

    public Integer getCurrentAccountId() {
        return currentAccountId;
    }

    public void setCurrentAccountId(Integer currentAccountId) {
        this.currentAccountId = currentAccountId;
    }
}
