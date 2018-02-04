import com.google.common.collect.Lists;
import com.lzj.Application;
import com.lzj.dao.FunctionDao;
import com.lzj.domain.Conference;
import com.lzj.domain.ConferenceFunction;
import com.lzj.domain.Function;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
@WebAppConfiguration
public class FunctionTest {
    @Autowired
    FunctionDao functionDao;
    @Test
    public void testFunction(){
        List<Function> groupFunctions = functionDao.findGroupFunction(2,3);
        List<Function> friendFunctions = functionDao.findFriendFunction(2,3);
        List<Function> list = Lists.newArrayList();
        list.addAll(groupFunctions);
        list.addAll(friendFunctions);
    }
    @Test
    public void list () {
        Conference conference = new Conference();
        conference.setId(6);

        List<ConferenceFunction> functions =functionDao.findConferenceFunctions(Lists.newArrayList(conference),2);
        System.out.println(functions);
    }
}
