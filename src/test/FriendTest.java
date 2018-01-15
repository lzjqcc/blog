import com.google.common.collect.Lists;
import com.lzj.Application;
import com.lzj.dao.FriendDao;
import com.lzj.dao.dto.FriendDto;
import com.lzj.domain.Friend;
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
public class FriendTest {
    @Autowired
    FriendDao friendDao;
    @Test
    public void testInsert(){
        Friend friend = new Friend();
        friend.setCurrentAccountId(1);
        friend.setFriendId(2);
        friend.setFunctionList(Lists.newArrayList(3, 4));
        friendDao.insertFriend(friend);
    }
    @Test
    public void testFindGroupFriend() {
        FriendDto dto = new FriendDto();
        dto.setCurrentAccountId(2);
        dto.setGroupId(1);

        List<Friend> list = friendDao.findFriends(dto);
        System.out.println(list);

    }
}
