import com.lzj.Application;
import com.lzj.dao.ConferenceDao;
import com.lzj.domain.Conference;
import com.lzj.domain.Page;
import com.lzj.service.impl.ConferenceServiceImpl;
import org.assertj.core.util.Lists;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

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

        conferenceService.send(Lists.newArrayList(3),conference);
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
}
