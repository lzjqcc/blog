package com.lzj.dao;

import com.lzj.annotation.EnableRelationTable;
import com.lzj.dao.dto.ConferenceDto;
import com.lzj.domain.Conference;
import com.lzj.domain.Function;
import com.lzj.domain.Page;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface ConferenceDao{
    @EnableRelationTable(relationTableName = "tb_conference_function")
    void insertConference(Conference conference);
    List<Conference> findConferencesBySponsorId(@Param("sponsorId") Integer sponsor, Page page);

    /**
     *
     * @param memeberId
     * @param page
     * @return
     */
    List<Conference> findConferencesByMemberId(@Param("memeberId") Integer memeberId, Page page);

    /**
     * 根据id 更新
     * @param conferenceDto
     */
    void updateConference(@Param("param") ConferenceDto conferenceDto);

    Conference findConferenceById(@Param("param") Integer conferenceId);

    /**
     * 级联删除 tb_conference_flow中对应的数据  cascad
     * @param conferenceId
     */
    void delete(Integer conferenceId);
}
