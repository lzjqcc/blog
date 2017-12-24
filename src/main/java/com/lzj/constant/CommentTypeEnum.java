package com.lzj.constant;

public enum  CommentTypeEnum {
    ARTICLE(0,"article"),PICTRUE(1,"picture");
    public int code;
    public String type;
    CommentTypeEnum(int code, String type) {
        this.code = code;
        this.type = type;
    }

}
