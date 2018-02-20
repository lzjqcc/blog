package com.lzj.dao.dto;

import java.io.Serializable;

public class ArticleDto implements Serializable {
    public static final long seriaId = 1L;
    private String title;//标题
    private String description;//描述
    //contentURL  resources/article/作者名称/分类（默认是default）/文章名.txt
    private String content;//这个字段保存文章内容，但不保存数据库中
    private Integer support;//点赞
    private Integer dislike;//点踩
    private Integer visitTimes;//访问次数
    private Integer currentAccountId;
    private Integer top;//置顶排序
    private boolean toTop;//是否置顶
    private String tips;//标签
    private String assortment;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getSupport() {
        return support;
    }

    public void setSupport(Integer support) {
        this.support = support;
    }

    public Integer getDislike() {
        return dislike;
    }

    public void setDislike(Integer dislike) {
        this.dislike = dislike;
    }

    public Integer getVisitTimes() {
        return visitTimes;
    }

    public void setVisitTimes(Integer visitTimes) {
        this.visitTimes = visitTimes;
    }

    public Integer getCurrentAccountId() {
        return currentAccountId;
    }

    public void setCurrentAccountId(Integer currentAccountId) {
        this.currentAccountId = currentAccountId;
    }

    public Integer getTop() {
        return top;
    }

    public void setTop(Integer top) {
        this.top = top;
    }

    public Boolean getToTop() {
        return toTop;
    }

    public void setToTop(Boolean toTop) {
        this.toTop = toTop;
    }

    public String getTips() {
        return tips;
    }

    public void setTips(String tips) {
        this.tips = tips;
    }

    public String getAssortment() {
        return assortment;
    }

    public void setAssortment(String assortment) {
        this.assortment = assortment;
    }
}
