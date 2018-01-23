package com.lzj.dao;

import com.lzj.annotation.EnableRelationTable;
import com.lzj.dao.dto.ConferenceDto;
import com.lzj.domain.Conference;
import com.lzj.domain.Function;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface ConferenceDao{
    @EnableRelationTable(relationTableName = "tb_conference_function")
    void insertConference(Conference conference);

}
