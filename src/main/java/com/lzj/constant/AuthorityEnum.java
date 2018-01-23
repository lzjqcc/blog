package com.lzj.constant;

/**
 * 权限
 */
public enum  AuthorityEnum {
    GROUP_PICTURE_GROUP_SEE(1,"group_picture_group_see","好友组查看相册权限"),
    GROUP_PICTURE_GROUP_COMMENT(2,"group_picture_group_comment","好友组相册评论权限"),
    FRIEND_PICTURE_GROUP_SEE(3,"friend_picture_group_see","好友查看相册权限"),
    FRIEND_PICTURE_GROUP_COMMENT(4,"friend_picture_group_comment","好友评论相册权限"),
    USER(5,"user","登录用户"),
    CONFERENCE_SEE(6, "conference_see", "会议查看"),
    CONFERENCE_UPDATE(7, "conference_update", "会议更新"),
    CONFERENCE_DELETE(8,"conference_delete","会议删除");
    public Integer id;
    public String authority;
    public String describe;
    AuthorityEnum(Integer id,String authority, String describe) {
        this.authority = authority;
        this.id = id;
        this.describe = describe;
    }
}
