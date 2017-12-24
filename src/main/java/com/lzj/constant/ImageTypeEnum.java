package com.lzj.constant;

public enum  ImageTypeEnum {
    HEADICON(1,"icon"),ARTICLEICON(2,"articlepic"),PICTUREICON(3,"picture");
    public int code;
    public String type;
    ImageTypeEnum(int code,String type) {
        this.code = code;
        this.type = type;
    }
}
