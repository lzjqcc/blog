import com.lzj.Application;
import com.lzj.dao.FriendDao;
import com.lzj.domain.Friend;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
public class FriendTest {
    @Autowired
    FriendDao friendDao;
    @Test
    public void testInsert(){
        Friend friend = new Friend();
        friend.setCurrentAccountId(1);
        friend.setFriendId(2);
        friendDao.insertFriend(friend);
    }
}
