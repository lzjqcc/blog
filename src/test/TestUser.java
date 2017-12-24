import com.lzj.Application;
import com.lzj.dao.AccountDao;
import com.lzj.dao.ArticleDao;
import com.lzj.domain.Account;
import com.lzj.domain.Page;
import com.lzj.service.AccountService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.List;

/**
 * Created by li on 17-8-6.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
public class TestUser {
    @Autowired
    private AccountDao userDao;
    @Autowired
    ArticleDao articleDao;

    @Test
    public void testInsert() {
        Account user = new Account();
        user.setUserName("大屁孩");
        user.setPassword("234");
        user.setEmail("1234@qq.com");
        userDao.insertAccount(user);
    }
    @Autowired
    AccountService userService;
/*    @Test
    public void testUpdate() {
        Account user = userService.findById(1);
        System.out.print(user + "-----------");
        user.setPassword("234");
        user.setUpdateTime(new Date());
        userService.updateUser(user);
        System.out.println(user.getUpdateTime());
    }*/
    @Test
    public void testfindById() {
        Page page = new Page();
        page.setPageSize(1);
        page.setCurrentPage(1);
        List<Account> list = userDao.findAll(page);
        System.out.print(list.size());
    }
 /*   @Test
    public void testFindByName(){
        User user =new User();
        user.setUserName("lzj");
        user.setPassword("lzj941005");
        User user1 = userDao.findByEmailOrNameAndPassword(user);
        System.out.print(user1);
    }*/

}
