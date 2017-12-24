package com.lzj.dao;

import com.lzj.annotation.EnableRelationTable;
import com.lzj.domain.Friend;
import com.lzj.domain.Function;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface FriendDao {
    @EnableRelationTable(relationTableName = "tb_friend_function",value = {"current_account_id","friend_id","function_id"})
    void insertFriend(Friend friend);
    /**
     * 查询好友权限
     * @param friendId
     * @param ownerId
     * @return
     */
    List<Function> findFriendFunction(Integer friendId, Integer ownerId);

    /**
     * 查询分组好友
     * @param groupId
     * @param ownerId
     * @return
     */
    List<Friend> findFriendByGroupId(Integer groupId, Integer ownerId);

    /**
     * 查询好友
     * @param friendId
     * @param ownerId
     * @return
     */
    Friend findFriendByFriendId(Integer friendId,Integer ownerId);
}
