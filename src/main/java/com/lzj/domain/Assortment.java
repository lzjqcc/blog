package com.lzj.domain;

public class Assortment extends BaseEntity {
    private String assortmentName;
    private Integer articleNum;
    private Integer currentAccountId;

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

    public Integer getCurrentAccountId() {
        return currentAccountId;
    }

    public void setCurrentAccountId(Integer currentAccountId) {
        this.currentAccountId = currentAccountId;
    }
}
