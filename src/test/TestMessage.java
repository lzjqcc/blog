import com.lzj.Application;
import com.lzj.dao.MessageDao;
import com.lzj.domain.MessageInfo;
import com.lzj.utils.ComentUtils;
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
public class TestMessage {
    @Autowired
    MessageDao messageDao;
    @Test
    public void testInsert(){
        MessageInfo info=new MessageInfo();

        info.setFlag(MessageInfo.FLAG.COMMENT_FLAG);
        info.setToUserId(1);
        info.setFromUserId(2);
        info.setType(true);
        messageDao.insertMessage(info);
    }
    @Test
    public void testSelect(){

       List<MessageInfo> list = messageDao.getMessages(ComentUtils.buildMessageCondition(1,true,null));
       System.out.println(list);
    }
}
