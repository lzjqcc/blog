import com.google.common.collect.Lists;
import com.lzj.Application;
import com.lzj.dao.FunctionDao;
import com.lzj.domain.Function;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
public class FunctionTest {
    @Autowired
    FunctionDao functionDao;
    @Test
    public void testFunction(){
      /*  List<Function> groupFunctions = functionDao.findGroupFunction(accountToken.getAccount().getId(),friendId);
        List<Function> friendFunctions = functionDao.findFriendFunction(accountToken.getAccount().getId(),friendId);
        List<Function> list = Lists.newArrayList();
        list.addAll(groupFunctions);
        list.addAll(friendFunctions);*/
    }

}
