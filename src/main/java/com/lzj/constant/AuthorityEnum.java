package com.lzj.constant;

/**
 * 权限
 */
public enum  AuthorityEnum {
    PICTURE_GROUP_SEE(1,"picture_group_see","相册查看"),
    PICTURE_GROUP_COMMENT(2,"picture_group_comment","相册评论"),
    USER(3,"user","登录用户");
    public Integer id;
    public String authority;
    public String describe;
    AuthorityEnum(Integer id,String authority, String describe) {
        this.authority = authority;
        this.id = id;
        this.describe = describe;
    }
}
