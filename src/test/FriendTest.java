import com.google.common.collect.Lists;
import com.lzj.Application;
import com.lzj.constant.FriendStatusEnum;
import com.lzj.dao.AccountDao;
import com.lzj.dao.FriendDao;
import com.lzj.dao.dto.FriendDto;
import com.lzj.domain.Account;
import com.lzj.domain.Friend;
import com.lzj.domain.Page;
import com.lzj.service.impl.FriendService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
@WebAppConfiguration
public class FriendTest {
    @Autowired
    FriendDao friendDao;
    @Autowired
    AccountDao accountDao;
    @Autowired
    FriendService friendService;
    private StringRedisTemplate redisTemplate = null;
    @Autowired
    ApplicationContext applicationContext;
    @Test
    public void testRedis(){
        FriendService friendService = applicationContext.getBean(FriendService.class, "a");
        FriendService friendService1 = applicationContext.getBean(FriendService.class, "b");
        System.out.println(friendService);
        System.out.println(friendService1);
    }
    @Test
    public void testApplyFriend() {
        FriendDto dto = new FriendDto();
        dto.setStatus(FriendStatusEnum.APPLE.code);
        dto.setCurrentAccountId(2);
        dto.setFriendId(3);
        dto.setGroupId(2);
        dto.setFriendName("小李子");
        friendService.friendApply(dto);
    }
    @Test
    public void test() {
        Account account = new Account();
        account.setBirth(new Date());
        accountDao.insertAccount(account);

    }
    @Test
    public void testSearch() {
        Page page = new Page();
        page.setCurrentPage(1);
        page.setPageSize(2);
        List<Account> list = accountDao.searchAccount(null,3, page);
        print(list);
        while (page.getCurrentPage() < page.getPageSize()) {
            page.setCurrentPage(page.getCurrentPage() + 1);
           list =  accountDao.searchAccount(null, 3, page);
            print(list);
        }
        int i = 0;
    }
    private void print(List<Account> list) {
        for (Account account : list) {
            System.out.println(account.getId());
        }
    }
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
