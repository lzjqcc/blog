package com.lzj.domain;

import java.io.Serializable;
public class PictureGroupFunction extends BaseEntity implements Serializable {
    private Integer currentAccountId;
    private Integer functionId;
    private Integer pictureGroupId;

    public Integer getCurrentAccountId() {
        return currentAccountId;
    }

    public void setCurrentAccountId(Integer currentAccountId) {
        this.currentAccountId = currentAccountId;
    }

    public Integer getFunctionId() {
        return functionId;
    }

    public void setFunctionId(Integer functionId) {
        this.functionId = functionId;
    }

    public Integer getPictureGroupId() {
        return pictureGroupId;
    }

    public void setPictureGroupId(Integer pictureGroupId) {
        this.pictureGroupId = pictureGroupId;
    }
}
