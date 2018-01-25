package com.lzj.dao;

import com.lzj.domain.Conference;
import com.lzj.domain.ConferenceFunction;
import com.lzj.domain.Function;
import org.apache.ibatis.annotations.Param;
import org.springframework.security.access.method.P;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FunctionDao {
    void insertFunctions(List<Function> functions);
    void insertFunction(Function function);

    List<Function> findAll();
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

    ConferenceFunction findConferenceFunction(@Param("conferenceId") Integer conferenceId, @Param("memberId") Integer memberId);

    /**
     *
     * @param conferences
     * @return
     */
    List<ConferenceFunction> findConferenceFunctions(@Param("param") List<Conference> conferences, @Param("memberId")Integer memberId);

}
