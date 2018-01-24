package com.lzj.service.impl;

import com.google.common.collect.Lists;
import com.lzj.VO.ResponseVO;
import com.lzj.constant.AuthorityEnum;
import com.lzj.dao.AccountDao;
import com.lzj.dao.ConferenceDao;
import com.lzj.domain.Account;
import com.lzj.domain.Conference;
import com.lzj.domain.EmailObject;
import com.lzj.domain.Page;
import com.lzj.utils.ComentUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConferenceServiceImpl {
    @Value("${email.template}")
    String emailTemplate;
    @Autowired
    ConferenceDao conferenceDao;
    @Autowired
    AccountDao accountDao;
    public ResponseVO findConferencesBySponsor(Integer sponsorId, Page page) {
        List<Conference> list = conferenceDao.findConferencesBySponsorId(sponsorId, page);

        return null;
    }
    public ResponseVO insertConference(Conference conference) {
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
            if (conference.getEmail()) {

            }
            return ComentUtils.buildResponseVO(true, "操做成功", conference);
        }
        return ComentUtils.buildResponseVO(false, "操作失败" , null);
    }
    public void send(List<Integer> memberIds, Conference conference) {

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
