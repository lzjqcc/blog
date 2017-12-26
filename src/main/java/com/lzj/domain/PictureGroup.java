package com.lzj.domain;

import com.lzj.annotation.EnableRelationTable;

import java.io.Serializable;
import java.util.List;

public class PictureGroup extends BaseEntity implements Serializable {
    private Integer currentAccountId;
    private String pictureGroupName;
    private String pictureDescribe;
    private List<Integer> functionList;
    @EnableRelationTable(relationTableName = "tb_picture_group_function",value = "function_id")
    public List<Integer> getFunctionList() {
        return functionList;
    }

    public void setFunctionList(List<Integer> functionList) {
        this.functionList = functionList;
    }

    @EnableRelationTable(relationTableName = "tb_picture_group_function",value = "picture_group_id")
    @Override
    public Integer getId() {
        return super.getId();
    }
    @EnableRelationTable(relationTableName = "tb_picture_group_function", value = "current_account_id")
    public Integer getCurrentAccountId() {
        return currentAccountId;
    }

    public void setCurrentAccountId(Integer currentAccountId) {
        this.currentAccountId = currentAccountId;
    }

    public String getPictureGroupName() {
        return pictureGroupName;
    }

    public void setPictureGroupName(String pictureGroupName) {
        this.pictureGroupName = pictureGroupName;
    }

    public String getPictureDescribe() {
        return pictureDescribe;
    }

    public void setPictureDescribe(String pictureDescribe) {
        this.pictureDescribe = pictureDescribe;
    }
}
