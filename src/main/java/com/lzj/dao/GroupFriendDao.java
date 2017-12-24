package com.lzj.dao;

import com.lzj.domain.Function;
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

}
