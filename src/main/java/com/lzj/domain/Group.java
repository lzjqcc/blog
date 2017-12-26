package com.lzj.domain;

import com.lzj.annotation.EnableRelationTable;

import java.io.Serializable;
import java.util.List;

public class Group extends BaseEntity implements Serializable{
    private String groupName;
    private String currentAccountId;
    private List<Integer> functionList;
    @EnableRelationTable(relationTableName = "tb_group_relation",value = "function_id",keyRow = true)
    public List<Integer> getFunctionList() {
        return functionList;
    }

    public void setFunctionList(List<Integer> functionList) {
        this.functionList = functionList;
    }
    @EnableRelationTable(relationTableName = "tb_group_relation",value = "group_id")
    @Override
    public Integer getId() {
        return super.getId();
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    @EnableRelationTable(relationTableName = "tb_group_relation", value = "current_account_id")
    public String getCurrentAccountId() {
        return currentAccountId;
    }

    public void setCurrentAccountId(String currentAccountId) {
        this.currentAccountId = currentAccountId;
    }
}
