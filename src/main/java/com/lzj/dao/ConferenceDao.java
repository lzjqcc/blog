package com.lzj.dao;

import com.lzj.annotation.EnableRelationTable;
import com.lzj.dao.dto.ConferenceDto;
import com.lzj.domain.Conference;
import com.lzj.domain.Function;

import java.util.List;

public interface ConferenceDao{
    @EnableRelationTable(relationTableName = "tb_conference_function")
    void insertConference(Conference conference);

    /**
     * currentAccountId
     * conferenceId
     * @param dto
     * @return
     */
    List<Function> findConferenceFunction(ConferenceDto dto);
}
