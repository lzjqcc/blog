package com.lzj.controller;

import com.lzj.VO.ResponseVO;
import com.lzj.domain.Conference;
import com.lzj.service.impl.ConferenceServiceImpl;
import com.lzj.utils.ComentUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/conferenceGroup")
public class ConferenceController {
    @Autowired
    ConferenceServiceImpl conferenceService;
    @RequestMapping(value = "/save",method = RequestMethod.POST)
    public ResponseVO insertConference(@RequestBody Conference conference) {
        conference.setSponsorId(ComentUtils.getCurrentAccount().getId());
        return conferenceService.insertConference(conference);
    }

    /**
     * 登录人查看自己参加的会议
     * @return
     */
    public ResponseVO<List<Conference>> findConferences() {
        return null;
    }
}
