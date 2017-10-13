package com.lzj.domain;

/**
 * Created by li on 17-8-6.
 */


public class Comment extends BaseEntity implements Cloneable{


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
    private Integer fromUserId;//哪个用户评论
    private Integer toUserId;//给哪个用户
    private Integer replayComentId;//回复评论的id

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

    public Integer getFromUserId() {
        return fromUserId;
    }

    public void setFromUserId(Integer fromUserId) {
        this.fromUserId = fromUserId;
    }

    public Integer getToUserId() {
        return toUserId;
    }

    public void setToUserId(Integer toUserId) {
        this.toUserId = toUserId;
    }

    public Integer getReplayComentId() {
        return replayComentId;
    }

    public void setReplayComentId(Integer replayComentId) {
        this.replayComentId = replayComentId;
    }
}
