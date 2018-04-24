package com.lzj.domain;

import java.io.Serializable;

public class MessageInfo extends BaseEntity implements Serializable{
    private String pushMessage;
    //发送系统广播消息时，toAccountId=-1 代表所有人，fromAccountId 表示admin
    private Integer fromAccountId;
    private String fromAccountName;
    private Integer toAccountId;
    private String toAccountName;
    public Integer getFriendId() {
        return friendId;
    }

    public void setFriendId(Integer friendId) {
        this.friendId = friendId;
    }

    // tb_friend中的id
    private Integer friendId;
    // 是否查看 false 没有查看 true查看
    private boolean type;
    //1,系统消息
    //2,评论消息
    //3,别人发送的私信息
    private Integer flag;
    public String getFromAccountName() {
        return fromAccountName;
    }

    public void setFromAccountName(String fromAccountName) {
        this.fromAccountName = fromAccountName;
    }

    public Integer getToAccountId() {
        return toAccountId;
    }

    public void setToAccountId(Integer toAccountId) {
        this.toAccountId = toAccountId;
    }

    public String getToAccountName() {
        return toAccountName;
    }

    public void setToAccountName(String toAccountName) {
        this.toAccountName = toAccountName;
    }

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }

    public String getPushMessage() {
        return pushMessage;
    }

    public void setPushMessage(String pushMessage) {
        this.pushMessage = pushMessage;
    }

    public Integer getFromAccountId() {
        return fromAccountId;
    }

    public void setFromAccountId(Integer fromAccountId) {
        this.fromAccountId = fromAccountId;
    }



    public Boolean getType() {
        return type;
    }

    public void setType(Boolean type) {
        this.type = type;
    }



    @Override
    public String toString() {
        return "MessageInfo{" +
                "pushMessage='" + pushMessage + '\'' +
                ", fromAccountId=" + fromAccountId +
                ", type=" + type +
                ", flag=" + flag +
                '}';
    }

    public static class   FLAG{
        public static Integer COMMENT_FLAG=2;
        public static Integer SYSTEM_FLAG=1;
        public static Integer PERSON_FLAG=3;
    }
}
