package com.lzj.dao;

import com.lzj.domain.Function;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FunctionDao {
    void insertFunction(Function function);

    /**
     *
     * @param currentAccountId 当前登录人
     * @param friendId
     * @return
     */
    List<Function> findFriendFunction(@Param("currentAccountId") Integer currentAccountId,
                                      @Param("friendId") Integer friendId);

    /**
     *
     * @param currentAccountId 当前登录人
     * @param friendId
     * @return
     */
    List<Function> findGroupFunction(@Param("currentAccountId") Integer currentAccountId,
                                     @Param("friendId") Integer friendId);

    List<Function> findConferenceFunction(@Param("conferenceId") Integer conferenceId, @Param("sponsorId") Integer sponsorId, @Param("memberId") Integer memberId);
}
