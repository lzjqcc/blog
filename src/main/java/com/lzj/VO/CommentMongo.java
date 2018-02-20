package com.lzj.VO;

import com.lzj.domain.BaseEntity;
import com.lzj.domain.Comment;

import java.util.List;

public class CommentMongo extends BaseEntity{
    private static final long serialVersionUID = -4502229365996582610L;
    private String comment;
    private Integer articleId;
    private Integer fromAccountId;//哪个用户评论
    private String fromAccountName;
    private String toAccountName;
    private Integer toAccountId;//给哪个用户
    private Integer currentAccountId;
    private List<Comment> list;

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

    public Integer getCurrentAccountId() {
        return currentAccountId;
    }

    public void setCurrentAccountId(Integer currentAccountId) {
        this.currentAccountId = currentAccountId;
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

    public List<Comment> getList() {
        return list;
    }

    public void setList(List<Comment> list) {
        this.list = list;
    }
}
