package com.lzj.domain;

import java.util.*;

/**
 * Created by li on 17-8-6.
 */

//@org.hibernate.annotations.Entity(selectBeforeUpdate = true,dynamicUpdate = true)
//@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class,property = "@id")
public class Article extends BaseEntity {
    private String title;//标题
    private String description;//描述
    private String contentURL;//文章内容保存在本地，这个字段用来保存内容的地址
    private String assortment;//文章分类
    private String url;
    private Integer support;//点赞
    private Integer disLike;//点踩
    private Integer userId;

    public String getAssortment() {
        return assortment;
    }

    public void setAssortment(String assortment) {
        this.assortment = assortment;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getSupport() {
        return support;
    }

    public void setSupport(Integer support) {
        this.support = support;
    }

    public Integer getDisLike() {
        return disLike;
    }

    public void setDisLike(Integer disLike) {
        this.disLike = disLike;
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

    public String getContentURL() {
        return contentURL;
    }

    public void setContentURL(String contentURL) {
        this.contentURL = contentURL;
    }


    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }


}
