package com.lzj.constant;

/**
 * 好友状态
 */
public enum FriendStatusEnum {
    APPLE(0,"申请"),AGREE(1,"同意"),REFUSE(2,"拒绝");
    public Integer code;
    public String value;
    FriendStatusEnum(Integer code, String value) {
        this.code= code;
        this.value = value;
    }
}
