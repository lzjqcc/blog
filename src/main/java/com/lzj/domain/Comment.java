package com.lzj.domain;

import java.io.Serializable;

/**
 * Created by li on 17-8-6.
 */


public class Comment extends BaseEntity implements Serializable{


    private static final long serialVersionUID = 8680640561261347063L;
    //用户评论组合  这个真是不错 time=*** FromUserSaid1
   /* @ElementCollection(targetClass = String.class)
    @MapKeyClass(value = String.class)
    @MapKeyColumn(name = "key")
    @Column(name = "comment")
    @CollectionTable(name = "")
    private Map<String,String> comments=new LinkedHashMap<>();*/
   // public interface CommentWithArticle{}
    private String comment;
    private Integer articleId;
    private Integer fromAccountId;//哪个用户评论
    private Integer toAccountId;//给哪个用户
    private Integer replayComentId;//回复评论的id
    private Integer source;
    private Integer currentAccountId;
    private Integer pictureGroupId;
    private String fromAccountName;
    private String toAccountName;

    public String getFromAccountName() {
        return fromAccountName;
    }

    public void setFromAccountName(String fromAccountName) {
        this.fromAccountName = fromAccountName;
    }

    public String getToAccountName() {
        return toAccountName;
    }

    public void setToAccountName(String toAccountName) {
        this.toAccountName = toAccountName;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Integer getArticleId() {
        return articleId;
    }

    public void setArticleId(Integer articleId) {
        this.articleId = articleId;
    }

    public Integer getFromAccountId() {
        return fromAccountId;
    }

    public void setFromAccountId(Integer fromAccountId) {
        this.fromAccountId = fromAccountId;
    }

    public Integer getToAccountId() {
        return toAccountId;
    }

    public void setToAccountId(Integer toAccountId) {
        this.toAccountId = toAccountId;
    }

    public Integer getReplayComentId() {
        return replayComentId;
    }

    public void setReplayComentId(Integer replayComentId) {
        this.replayComentId = replayComentId;
    }

    public Integer getSource() {
        return source;
    }

    public void setSource(Integer source) {
        this.source = source;
    }

    public Integer getCurrentAccountId() {
        return currentAccountId;
    }

    public void setCurrentAccountId(Integer currentAccountId) {
        this.currentAccountId = currentAccountId;
    }

    public Integer getPictureGroupId() {
        return pictureGroupId;
    }

    public void setPictureGroupId(Integer pictureGroupId) {
        this.pictureGroupId = pictureGroupId;
    }
}
