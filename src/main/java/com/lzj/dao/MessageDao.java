package com.lzj.dao;

import com.lzj.domain.MessageInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository("messageDao")
public interface MessageDao {
    void insertMessage(MessageInfo info);

    /**
     * key为表message的列名
     * @param map
     * @return
     */
    List<MessageInfo> getMessages(@Param("map") Map<String,Object> map);
    void updateType(@Param("type") Boolean type,@Param("id")Integer id);
}
