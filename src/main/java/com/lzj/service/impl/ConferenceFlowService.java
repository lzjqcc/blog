package com.lzj.service.impl;

import com.lzj.VO.ResponseVO;
import com.lzj.dao.ConferenceFlowDao;
import com.lzj.domain.ConferenceFlow;
import com.lzj.utils.ComentUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConferenceFlowService {
    @Autowired
    ConferenceFlowDao conferenceFlowDao;
    public ResponseVO<List<ConferenceFlow>> findConferenceFlows(Integer conferenceId) {
        return ComentUtils.buildResponseVO(true, "操作成功", conferenceFlowDao.findConferenceFlowsByConferenceId(conferenceId));
    }
    public ResponseVO update(ConferenceFlow conferenceFlow) {
        conferenceFlowDao.update(conferenceFlow);
        return ComentUtils.buildResponseVO(true, "操作成功", null);
    }
    public ResponseVO delete(Integer conferenceId) {
        int i = conferenceFlowDao.delete(conferenceId);
        if (i > 0) {
            return ComentUtils.buildResponseVO(true, "操作成功", null);
        }
        return ComentUtils.buildResponseVO(false, "操作失败", null);
    }
}
