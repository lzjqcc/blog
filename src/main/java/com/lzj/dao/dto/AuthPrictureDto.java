package com.lzj.dao.dto;

import java.io.Serializable;
import java.security.SecureRandom;

public class AuthPrictureDto implements Serializable {
    private static final long serialVersionUID = -1730649115329465454L;
    private Integer id;
    private String friendName;
    private Boolean lookPictureAuth;
    private Boolean commentPictureAuth;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFriendName() {
        return friendName;
    }

    public void setFriendName(String friendName) {
        this.friendName = friendName;
    }

    public Boolean getLookPictureAuth() {
        if (lookPictureAuth == null) {
            return false;
        }
        return lookPictureAuth;
    }

    public void setLookPictureAuth(Boolean lookPictureAuth) {
        this.lookPictureAuth = lookPictureAuth;
    }

    public Boolean getCommentPictureAuth() {
        if (commentPictureAuth == null) {
            return false;
        }
        return commentPictureAuth;
    }

    public void setCommentPictureAuth(Boolean commentPictureAuth) {
        this.commentPictureAuth = commentPictureAuth;
    }
}
