package com.lzj.dao.dto;

public class FunctionDto {
    // 好友id
    private Integer friendId;
    // 相册分组id
    private Integer pictureGroupId;
    //好友分组id
    private Integer groupFriendId;
    // 会议id
    private Integer conferenceId;

    public Integer getConferenceId() {
        return conferenceId;
    }

    public void setConferenceId(Integer conferenceId) {
        this.conferenceId = conferenceId;
    }

    public Integer getFriendId() {
        return friendId;
    }

    public void setFriendId(Integer friendId) {
        this.friendId = friendId;
    }

    public Integer getPictureGroupId() {
        return pictureGroupId;
    }

    public void setPictureGroupId(Integer pictureGroupId) {
        this.pictureGroupId = pictureGroupId;
    }

    public Integer getGroupFriendId() {
        return groupFriendId;
    }

    public void setGroupFriendId(Integer groupFriendId) {
        this.groupFriendId = groupFriendId;
    }
}
