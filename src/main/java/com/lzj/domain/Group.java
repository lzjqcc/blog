package com.lzj.domain;

import com.lzj.annotation.EnableRelationTable;

import java.io.Serializable;
import java.util.List;

public class Group extends BaseEntity implements Serializable{
    private String groupName;
    private Integer currentAccountId;
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
    public Integer getCurrentAccountId() {
        return currentAccountId;
    }

    public void setCurrentAccountId(Integer currentAccountId) {
        this.currentAccountId = currentAccountId;
    }
}
