package com.lzj.constant;

/**
 * 权限： 相册权限，会议权限，主站权限
 */
public enum  FunctionTypeEnum {
    ALBUM(1,"album"),CONFERENCE(2,"conference"),MAINSITE(3,"main_site");
    public int code;
    public String type;
    FunctionTypeEnum(int code,String type) {
        this.code = code;
        this.type = type;
    }
}

