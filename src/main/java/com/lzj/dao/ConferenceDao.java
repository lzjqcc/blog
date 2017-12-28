package com.lzj.dao;

import com.lzj.annotation.EnableRelationTable;
import com.lzj.domain.Conference;

public interface ConferenceDao {
    @EnableRelationTable(relationTableName = "tb_conference_function")
    void insertConference(Conference conference);
}
