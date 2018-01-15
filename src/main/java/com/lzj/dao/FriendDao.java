package com.lzj.dao;

import com.lzj.annotation.EnableRelationTable;
import com.lzj.dao.dto.FriendDto;
import com.lzj.domain.Friend;
import com.lzj.domain.Function;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface FriendDao extends BaseDao{
    @EnableRelationTable(relationTableName = "tb_friend_function",value = {"current_account_id","friend_id","function_id"})
    void insertFriend(Friend friend);
    void updateFriend(FriendDto dto);

    void deleteFriend(FriendDto friendDto);

    List<Friend> findFriends(FriendDto friendDto);
}
