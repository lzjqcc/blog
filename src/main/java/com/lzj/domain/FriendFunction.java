package com.lzj.domain;

import java.io.Serializable;

/**
 * 多对多 关联表   现在会自动插入
 *
 */
public class FriendFunction extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 4165987871333934186L;
    private Integer currentAccountId;
    private Integer friendId;
    private Integer functionId;
    private String auth;

    public String getAuth() {
        return auth;
    }

    public void setAuth(String auth) {
        this.auth = auth;
    }

    public Integer getCurrentAccountId() {
        return currentAccountId;
    }

    public void setCurrentAccountId(Integer currentAccountId) {
        this.currentAccountId = currentAccountId;
    }

    public Integer getFriendId() {
        return friendId;
    }

    public void setFriendId(Integer friendId) {
        this.friendId = friendId;
    }

    public Integer getFunctionId() {
        return functionId;
    }

    public void setFunctionId(Integer functionId) {
        this.functionId = functionId;
    }
}
