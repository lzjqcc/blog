package com.lzj.dao;

import com.lzj.dao.dto.FriendDto;
import com.lzj.domain.Function;
import com.lzj.domain.GroupFriend;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface GroupFriendDao {
    /**
     *  查询分组的权限
     * @param groupId 好友分组id
     * @param ownerId 分组的所有者
     * @return
     */
    List<Function> findGroupFunction(Integer groupId,Integer ownerId);

    void insertGroupFriend(GroupFriend groupFriend);

    /**
     * currentAccount friend 确定唯一记录
     * @param dto
     */
    void updateGroupFriend(FriendDto dto);

    public void delete(GroupFriend friend);
}
