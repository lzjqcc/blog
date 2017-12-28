package com.lzj.dao;

import com.lzj.annotation.EnableRelationTable;
import com.lzj.dao.dto.FriendDto;
import com.lzj.domain.Friend;
import com.lzj.domain.Function;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface FriendDao {
    @EnableRelationTable(relationTableName = "tb_friend_function",value = {"current_account_id","friend_id","function_id"})
    void insertFriend(Friend friend);
    void updateFriend(FriendDto dto);

    void deleteFriend(FriendDto friendDto);
    /**
     * 查询好友权限
     * @param friendId
     * @param ownerId
     * @return
     */
    List<Function> findFriendFunction(Integer friendId, Integer ownerId);

    /**
     * 查询分组好友
     * @param friendDto
     * @return
     */
    List<Friend> findGroupFriendsByDto(FriendDto friendDto);
}
