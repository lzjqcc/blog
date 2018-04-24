package com.lzj.domain;

import com.lzj.annotation.EnableRelationTable;

import java.io.Serializable;
import java.util.List;

public class PictureGroup extends BaseEntity implements Serializable {
    private Integer currentAccountId;
    private String pictureGroupName;
    private String pictureDescribe;
    @Override
    public Integer getId() {
        return super.getId();
    }
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
