package com.lzj.service.impl;

import com.google.common.collect.Lists;
import com.lzj.VO.ResponseVO;
import com.lzj.constant.AuthorityEnum;
import com.lzj.dao.AccountDao;
import com.lzj.dao.ConferenceDao;
import com.lzj.dao.ConferenceFlowDao;
import com.lzj.dao.FunctionDao;
import com.lzj.dao.dto.ConferenceDto;
import com.lzj.domain.*;
import com.lzj.utils.ComentUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ConferenceServiceImpl {
    @Value("${email.template}")
    String emailTemplate;
    @Autowired
    ConferenceDao conferenceDao;
    @Autowired
    AccountDao accountDao;
    @Autowired
    FunctionDao functionDao;
    @Autowired
    ConferenceFlowDao conferenceFlowDao;
    /**
     * 查看自己参加的会议
     * @param memberId
     * @param page
     * @return
     */
    public ResponseVO<List<ConferenceDto>> findConferenceBymemberId(Integer memberId, Page page) {
        List<ConferenceDto> conferenceDtos = new ArrayList<>(64);
        List<Conference> list = conferenceDao.findConferencesByMemberId(memberId, page);
        List<ConferenceFunction> functions = functionDao.findConferenceFunctions(list, memberId);
        Map<Integer, Conference> map = list.stream().collect(Collectors.toMap(Conference::getId, t -> t));
        for (ConferenceFunction function : functions) {
            List<Integer> integers = function.getFunctionIds();
            for (Integer id : integers) {
                if (id.equals(AuthorityEnum.CONFERENCE_SEE.id)) {
                    conferenceDtos.add(buildDto(map.get(function.getConferenceId())));
                }
            }
        }
        return ComentUtils.buildResponseVO(true, "操做成功", conferenceDtos);
    }
    /**
     * 会议发起人查看自己发起的会议
     * @param sponsorId
     * @param page
     * @return
     */
    public ResponseVO<List<ConferenceDto>> findConferencesBySponsor(Integer sponsorId, Page page) {
        List<Conference> list = conferenceDao.findConferencesBySponsorId(sponsorId, page);
        List<ConferenceDto> conferenceDtos = new ArrayList<>(64);
        for (Conference conference : list) {
            conferenceDtos.add(buildDto(conference));
        }
        return ComentUtils.buildResponseVO(true, "操做成功", conferenceDtos);
    }
    public ResponseVO updateConference(ConferenceDto dto) {
        if (dto.getId() == null) {
            return ComentUtils.buildResponseVO(false, "操作失败", null);
        }
        Conference conference = conferenceDao.findConferenceById(dto.getId());
        //
        if (!conference.getSponsorId().equals(dto.getSponsorId())) {
            List<ConferenceFunction> functions =functionDao.findConferenceFunctions(Lists.newArrayList(conference), dto.getSponsorId());
            if (!functions.contains(AuthorityEnum.CONFERENCE_UPDATE)) {
                return ComentUtils.buildResponseVO(false, "没有权限操作", null);
            }
        }
        conferenceDao.updateConference(dto);
        return ComentUtils.buildResponseVO(true, "操做成功", null);
    }
    private ConferenceDto buildDto(Conference conference) {
        ConferenceDto dto = new ConferenceDto();
        BeanUtils.copyProperties(conference, dto);
        return dto;
    }

    /**
     * 创建会议是要创建会议流程
     * @param conference
     * @param conferenceFlows
     * @return
     */
    public ResponseVO insertConference(Conference conference, List<ConferenceFlow> conferenceFlows) {
        conference.setFunctionList(Lists.newArrayList(
                AuthorityEnum.CONFERENCE_SEE.id,
                AuthorityEnum.CONFERENCE_UPDATE.id
                ));
        if (conference.getMemberIds() == null) {
            return ComentUtils.buildResponseVO(false, "操作失败,没有添加会议成员", null);
        }
        if (conference.getTheme() == null) {
            return ComentUtils.buildResponseVO(false, "操作失败,没有会议主题", null);
        }
        if (conference.getPlace() == null) {
            return ComentUtils.buildResponseVO(false, "操作失败,没有会议地点", null);
        }
        conferenceDao.insertConference(conference);
        if (conference.getId() != null) {
            for (ConferenceFlow flow : conferenceFlows) {
                flow.setConferenceId(conference.getId());
            }
            conferenceFlowDao.insertConferenceFlows(conferenceFlows);
            if (conference.getEmail()) {
                send(conference.getMemberIds(), conference);
            }
            return ComentUtils.buildResponseVO(true, "操做成功", conference);
        }
        return ComentUtils.buildResponseVO(false, "操作失败" , null);
    }
    private void send(List<Integer> memberIds, Conference conference) {

        List<Account> list = accountDao.findAccountsByIds(memberIds);
        for (Account account : list) {
            sendEmail(account, conference);
        }

    }
    private void sendEmail(Account account, Conference conference) {
        EmailObject emailObject = new EmailObject();
        emailObject.setSendTo(account.getEmail());
        emailObject.setSubject(conference.getTheme());
        emailObject.setDefaultEncoding("utf-8");
        String emailTemplate = "Hi, %s 先生你好，%s 邀请你参加 %s 会议，会议地点%s ,会议开始时间 %tF %tT 会议结束时间%tF %tT";
        emailObject.setContent(String.format(emailTemplate,account.getUserName(),
                ComentUtils.getCurrentAccount().getUserName(),conference.getTheme(),conference.getPlace(),
                conference.getBeginTime(),conference.getBeginTime(),conference.getEndTime(),conference.getEndTime()));
        ComentUtils.sendEmail(emailObject);
    }
}
