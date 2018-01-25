package com.lzj.dao;

import com.lzj.domain.ConferenceFlow;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface ConferenceFlowDao {
    int insertConferenceFlow(ConferenceFlow flow);

    /**
     * 批量插入不要使用@param
     * @param flows
     */
    void insertConferenceFlows( List<ConferenceFlow> flows);
    List<ConferenceFlow> findConferenceFlowsByConferenceId(Integer conferenceId);

    void update(ConferenceFlow conferenceFlow);
}
