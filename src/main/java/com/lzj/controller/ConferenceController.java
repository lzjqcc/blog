package com.lzj.controller;

import com.lzj.VO.ResponseVO;
import com.lzj.dao.dto.ConferenceDto;
import com.lzj.domain.Conference;
import com.lzj.domain.ConferenceFlow;
import com.lzj.domain.Page;
import com.lzj.service.impl.ConferenceServiceImpl;
import com.lzj.utils.ComentUtils;
import org.apache.commons.collections.CollectionUtils;
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
    public static class Dto {
        Conference conference;
        List<ConferenceFlow> conferenceFlows;

        public Conference getConference() {
            return conference;
        }

        public void setConference(Conference conference) {
            this.conference = conference;
        }

        public List<ConferenceFlow> getConferenceFlows() {
            return conferenceFlows;
        }

        public void setConferenceFlows(List<ConferenceFlow> conferenceFlows) {
            this.conferenceFlows = conferenceFlows;
        }
    }

    /**
     * {"conference":{"memberIds":[2,3],"theme":"他阿萨德来看","place":"上饶"},"conferenceFlows":[{"describe":"李志坚发表"},{"describe":"瞿超超发表"}]}
     * @param dto
     * @return
     */
    @RequestMapping(value = "/save",method = RequestMethod.POST)
    public ResponseVO insertConference(@RequestBody Dto dto) {
        if (dto.getConference() != null && CollectionUtils.isNotEmpty(dto.getConferenceFlows())){
            dto.getConference().setSponsorId(ComentUtils.getCurrentAccount().getId());
            return conferenceService.insertConference(dto.getConference(), dto.getConferenceFlows());
        }
        return ComentUtils.buildResponseVO(false, "操作失败", null);
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
        dto.setSponsorId(ComentUtils.getCurrentAccount().getId());
        return conferenceService.updateConference(dto);
    }
    @RequestMapping(value = "delete", method = RequestMethod.GET)
    public ResponseVO delete(@RequestParam("conferenceId") Integer conferenceId) {
        return conferenceService.delete(conferenceId);
    }

}
