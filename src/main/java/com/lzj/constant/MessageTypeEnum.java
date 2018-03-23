package com.lzj.constant;


public enum MessageTypeEnum {
    SYSTEM(1, "系统消息"),
    COMMENT(2, "评论消息"),
    CHAT(3, "聊天消息"),
    FRIEND_APPLY(4, "好友申请"),
    FRIEND_AGREE(5, "好友同意");
    public Integer code;
    public String describtion;

    MessageTypeEnum(Integer code, String describtion) {
        this.code = code;
        this.describtion = describtion;
    }
}
