package com.lzj.controller;

import com.lzj.VO.ResponseVO;
import com.lzj.dao.ConferenceFlowDao;
import com.lzj.domain.ConferenceFlow;
import com.lzj.service.impl.ConferenceFlowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("conferenceFlow")
public class ConferenceFlowController {
    @Autowired
    ConferenceFlowService conferenceFlowService;
    @RequestMapping(value = "findCOnferenceFlows",method = RequestMethod.GET)
    public ResponseVO<List<ConferenceFlow>> findConferenceFlows(Integer conferenceId) {
        return conferenceFlowService.findConferenceFlows(conferenceId);
    }
    @RequestMapping(value = "update",method = RequestMethod.POST)
    public ResponseVO update(@RequestBody ConferenceFlow conferenceFlow) {
        return conferenceFlowService.update(conferenceFlow);
    }
    @RequestMapping(value = "delete", method = RequestMethod.GET)
    public ResponseVO delete(@RequestParam("conferenceFlowId") Integer conferenceFlowId) {
        return conferenceFlowService.delete(conferenceFlowId);
    }
}
