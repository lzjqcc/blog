package com.lzj.domain;

import java.io.Serializable;

public class MessageInfo extends BaseEntity implements Serializable{
    private String content;
    //发送系统广播消息时，toAccountId=-1 代表所有人，fromAccountId 表示admin
    private Integer fromAccountId;
    private Integer toAccountId;
    private Boolean type;
    //1,系统消息
    //2,评论消息
    //3,别人发送的私信息
    private Integer flag;

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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

    public Boolean getType() {
        return type;
    }

    public void setType(Boolean type) {
        this.type = type;
    }



    @Override
    public String toString() {
        return "MessageInfo{" +
                "content='" + content + '\'' +
                ", fromAccountId=" + fromAccountId +
                ", toAccountId=" + toAccountId +
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
