package com.lzj.dao;

import com.lzj.dao.dto.MessageDto;
import com.lzj.domain.MessageInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository("messageDao")
public interface MessageDao {
    void insertMessage(MessageInfo info);

    List<MessageInfo> findMessagesByDto(@Param("param") MessageDto dto);
    void updateType(@Param("type") Boolean type,@Param("id")Integer id);
}
