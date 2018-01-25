package com.lzj.service.impl;

import com.lzj.dao.ConferenceFlowDao;
import com.lzj.domain.ConferenceFlow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConferenceFlowService {
    @Autowired
    ConferenceFlowDao conferenceFlowDao;
    public List<ConferenceFlow> findConferenceFlows(Integer conferenceId) {
        return conferenceFlowDao.findConferenceFlowsByConferenceId(conferenceId);
    }
}
