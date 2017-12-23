package com.lzj.domain;

/**
 * Created by li on 17-8-6.
 */

//@org.hibernate.annotations.Entity(selectBeforeUpdate = true,dynamicUpdate = true)
//@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class,property = "@id")
public class Article extends BaseEntity {
    private String title;//标题
    private String description;//描述
    //contentURL  resources/article/作者名称/分类（默认是default）/文章名.txt
    private String content;//这个字段保存文章内容，但不保存数据库中
    private Integer support;//点赞
    private Integer dislike;//点踩
    private Integer visitTimes;//访问次数
    private Integer currentAccountId;
    private Integer top;//置顶排序
    private Boolean toTop;//是否置顶
    private Integer assortmentId;
    private String tips;//标签

    public String getTips() {
        return tips;
    }

    public void setTips(String tips) {
        this.tips = tips;
    }

    public Integer getAssortmentId() {
        return assortmentId;
    }

    public void setAssortmentId(Integer assortmentId) {
        this.assortmentId = assortmentId;
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

    public Integer getVisitTimes() {
        return visitTimes;
    }

    public void setVisitTimes(Integer visitTimes) {
        this.visitTimes = visitTimes;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getCurrentAccountId() {
        return currentAccountId;
    }

    public void setCurrentAccountId(Integer currentAccountId) {
        this.currentAccountId = currentAccountId;
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

    @Override
    public String toString() {
        return "Article{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", content='" + content + '\'' +
                ", support=" + support +
                ", dislike=" + dislike +
                ", visitTimes=" + visitTimes +
                ", currentAccountId=" + currentAccountId +
                ", top=" + top +
                ", toTop=" + toTop +
                ", assortmentId=" + assortmentId +
                '}';
    }
}
