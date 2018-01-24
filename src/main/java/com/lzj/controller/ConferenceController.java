package com.lzj.controller;

import com.lzj.VO.ResponseVO;
import com.lzj.dao.dto.ConferenceDto;
import com.lzj.domain.Conference;
import com.lzj.domain.Page;
import com.lzj.service.impl.ConferenceServiceImpl;
import com.lzj.utils.ComentUtils;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.RegEx;
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
     * 登录人查看自己发起的会议
     * @return
     */
    @RequestMapping(value = "/findConferences",method = RequestMethod.GET)
    public ResponseVO<List<ConferenceDto>> findConferences(@RequestParam("pageSize") Integer pageSize,
                                                           @RequestParam("currentPage") Integer currentPage) {
        Page page = new Page();
        page.setCurrentPage(currentPage);
        page.setPageSize(pageSize);
       return conferenceService.findConferencesBySponsor(ComentUtils.getCurrentAccount().getId(), page);
    }

    /**
     * 登录人查看自己参加的会议
     * @param pageSize
     * @param currentPage
     * @return
     */
    @RequestMapping(value = "/findConferencesByMemberId",method = RequestMethod.GET)
    public ResponseVO<List<ConferenceDto>> findConferencesByMemberId(@RequestParam("pageSize") Integer pageSize,
                                                                     @RequestParam("currentPage") Integer currentPage) {
        Page page = new Page();
        page.setCurrentPage(currentPage);
        page.setPageSize(pageSize);
        return conferenceService.findConferenceBymemberId(ComentUtils.getCurrentAccount().getId(), page);
    }

    /**
     * 更新会议
     * @param dto
     * @return
     */
    @RequestMapping(value = "updateConference", method = RequestMethod.POST)
    public ResponseVO updateConference(@RequestBody ConferenceDto dto){
        return conferenceService.updateConference(dto);
    }

}
