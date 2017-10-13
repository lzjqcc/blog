package com.lzj.domain;

import com.lzj.domain.BaseEntity;

public class Assortment extends BaseEntity {
    private String assortmentName;
    private Integer articleNum;
    private Integer userId;

    public String getAssortmentName() {
        return assortmentName;
    }

    public void setAssortmentName(String assortmentName) {
        this.assortmentName = assortmentName;
    }

    public Integer getArticleNum() {
        return articleNum;
    }

    public void setArticleNum(Integer articleNum) {
        this.articleNum = articleNum;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}
