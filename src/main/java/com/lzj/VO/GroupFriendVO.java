package com.lzj.VO;

import com.lzj.dao.dto.GroupDto;
import com.lzj.domain.Friend;

import java.io.Serializable;
import java.util.List;

public class GroupFriendVO implements Serializable {
    private static final long serialVersionUID = -442378781861743L;
    public GroupDto groupDto;
    public List<Friend> friends;

    public GroupDto getGroupDto() {
        return groupDto;
    }

    public void setGroupDto(GroupDto groupDto) {
        this.groupDto = groupDto;
    }

    public List<Friend> getFriends() {
        return friends;
    }

    public void setFriends(List<Friend> friends) {
        this.friends = friends;
    }
}
