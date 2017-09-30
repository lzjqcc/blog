import com.lzj.Application;
import com.lzj.dao.ArticleDao;
import com.lzj.dao.UserDao;
import com.lzj.domain.Article;
import com.lzj.domain.User;
import com.lzj.service.ArticleService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.Date;

/**
 * Created by li on 17-8-6.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
public class TestUser {
    @Autowired
    private UserDao userDao;

    @Test
    public void testInsert() {
        User user = new User();
        user.setUserName("李志坚");
        user.setPassword("123");
        user.setRole(User.Role.ROLE_ADMIN);
        user.setEmail("121233@qq.com");
        int id = userDao.insertUser(user);
        System.out.println(id + ":-----------");
    }

    @Test
    public void testUpdate() {
        User user = userDao.findById(1);
        System.out.print(user + "-----------");
        user.setPassword("234");
        user.setUpdateTime(new Date());
        userDao.updateUser(user);
        System.out.println(user.getUpdateTime());
    }

    @Test
    public void testfindById() {
        User user = userDao.findById(1);
        System.out.print(user + "-----------------");
    }

}
