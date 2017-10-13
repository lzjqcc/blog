package com.lzj.VO;

import com.lzj.domain.BaseEntity;
import com.lzj.domain.Comment;

import java.util.List;

public class CommentMongo extends BaseEntity{
    private String comment;
    private Integer articleId;
    private Integer fromUserId;//哪个用户评论
    private Integer toUserId;//给哪个用户
    private List<Comment> list;

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

    public List<Comment> getList() {
        return list;
    }

    public void setList(List<Comment> list) {
        this.list = list;
    }
}
