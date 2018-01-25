import com.lzj.Application;
import com.lzj.dao.ConferenceDao;
import com.lzj.dao.ConferenceFlowDao;
import com.lzj.dao.dto.ConferenceDto;
import com.lzj.domain.Conference;
import com.lzj.domain.ConferenceFlow;
import com.lzj.domain.Page;
import com.lzj.service.impl.ConferenceServiceImpl;
import org.assertj.core.util.Lists;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
public class ConferenceTest {
    @Autowired
    ConferenceDao conferenceDao;
    @Autowired
    ConferenceServiceImpl conferenceService;
    @Autowired
    ConferenceFlowDao conferenceFlowDao;
    @Test
    public void beach() {
        ConferenceFlow flow = new ConferenceFlow();
        flow.setConferenceId(6);
        flow.setDescribe("李志坚发表");
        ConferenceFlow flow1 = new ConferenceFlow();
        flow1.setConferenceId(6);
        flow1.setDescribe("瞿超超发表");
        conferenceFlowDao.insertConferenceFlows(Lists.newArrayList(flow, flow1));

    }
    @Test
    public void testInsert() {
        Conference conference = new Conference();
        conference.setBeginTime(new Date());
        conference.setTheme("讨论明天吃什么");
        conference.setSponsorId(5);
        List<Integer> members = Lists.newArrayList(2,3,4);
        List<Integer> functions = Lists.newArrayList(1,2);
        conference.setMemberIds(members);
        conference.setFunctionList(functions);
        conferenceDao.insertConference(conference);
    }
    @Test
    public void send(){
        Conference conference = new Conference();
        conference.setPlace("南昌");
        conference.setTheme("讨论吃饭");
        conference.setBeginTime(new Date());
        conference.setEndTime(new Date());

        //conferenceService.send(Lists.newArrayList(3),conference);
    }
    @Test
    public void findConference() {
        Page page = new Page();
        page.setPageSize(1);
        page.setCurrentPage(1);
        List<Conference> list =conferenceDao.findConferencesByMemberId(2, page);
        System.out.println(list.size());
        System.out.println(page);
    }
    @Test
    public void findConferenceBySponsorid() {
        Page page = new Page();
        page.setPageSize(2);
        page.setCurrentPage(1);
        List<Conference> list = conferenceDao.findConferencesBySponsorId(5, page);
        System.out.println(list.size());
        System.out.println(page);
    }
    @Test
    public void update() {
        ConferenceDto dto = new ConferenceDto();
        dto.setId(6);
        dto.setBeginTime(new Date());
        conferenceDao.updateConference(dto);
    }
    @Test
    public void list () {

    }
}
