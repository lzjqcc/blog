package com.lzj.domain;

import com.sun.xml.internal.rngom.parse.host.Base;
import net.minidev.json.annotate.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;

/**
 * Created by li on 17-8-6.
 */
@Entity
@Table(name = "commet")

public class Comment extends BaseEntity implements Cloneable{


    //用户评论组合  这个真是不错 time=*** FromUserSaid1
   /* @ElementCollection(targetClass = String.class)
    @MapKeyClass(value = String.class)
    @MapKeyColumn(name = "key")
    @Column(name = "comment")
    @CollectionTable(name = "")
    private Map<String,String> comments=new LinkedHashMap<>();*/
    public interface CommentWithArticle{}
    private Date createTime;
    private String comments;

    @ManyToOne
    private User fromUser;//哪个用户评论
    @ManyToOne
    private User toUser;//给哪个用户
    @ManyToOne
    @com.fasterxml.jackson.annotation.JsonIgnore
    private Comment parent;
    @Transient
    private List<Comment> children;
    public Comment getParent() {
        return parent;
    }

    public void setParent(Comment parent) {
        this.parent = parent;
    }

    public List<Comment> getChildren() {
        return children;
    }

    public void setChildren(List<Comment> children) {
        this.children = children;
    }

    @Override
    public Date getCreateTime() {
        return createTime;
    }

    @Override
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }


    public User getFromUser() {
        return fromUser;
    }

    public void setFromUser(User fromUser) {
        this.fromUser = fromUser;
    }

    public User getToUser() {
        return toUser;
    }

    public void setToUser(User toUser) {
        this.toUser = toUser;
    }
  /*  @Transient
    @com.fasterxml.jackson.annotation.JsonIgnore
    public Comment getThis() throws CloneNotSupportedException {
        Comment comment= (Comment) this.clone();

        return comment;
    }*/
}
