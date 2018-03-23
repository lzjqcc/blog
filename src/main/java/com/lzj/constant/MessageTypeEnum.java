package com.lzj.constant;


public enum MessageTypeEnum {
    SYSTEM(1, "系统消息"),
    COMMENT(2, "评论消息"),
    CHAT(3, "聊天消息");
    public Integer code;
    public String describtion;

    MessageTypeEnum(Integer code, String describtion) {
        this.code = code;
        this.describtion = describtion;
    }
}
