package com.lzj.domain;

import com.sun.xml.internal.rngom.parse.host.Base;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by li on 17-8-6.
 */
@Entity
@Table(name = "commet")
public class Comment extends BaseEntity{

    @Column(name = "user_comment")
    @Lob
    private String userComent;//用户评论组合  这个真是不错 time=*** FromUserSaid1

    @ManyToOne
    private User fromUser;//哪个用户评论
    @ManyToOne
    private User toUser;//给哪个用户

    private String type;//1 表示回复，2表示直接评论

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUserComent() {
        return userComent;
    }

    public void setUserComent(String userComent) {
        this.userComent = userComent;
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

    @Override
    public String toString() {
        return "Comment{" +
                "userComent='" + userComent + '\'' +
                ", fromUser=" + fromUser +
                ", toUser=" + toUser +
                ", type='" + type + '\'' +
                '}';
    }
}
