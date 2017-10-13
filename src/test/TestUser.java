import com.lzj.Application;
import com.lzj.dao.ArticleDao;
import com.lzj.dao.UserDao;
import com.lzj.domain.Article;
import com.lzj.domain.User;
import com.lzj.service.ArticleService;
import com.lzj.service.UserService;
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
    @Autowired
    ArticleDao articleDao;

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
    @Autowired
    UserService userService;
    @Test
    public void testUpdate() {
        User user = userService.findById(1);
        System.out.print(user + "-----------");
        user.setPassword("234");
        user.setUpdateTime(new Date());
        userService.updateUser(user);
        System.out.println(user.getUpdateTime());
    }

    @Test
    public void testfindById() {
        User user = userDao.findById(1);
        User user1 = userDao.findById(2);

        User user2=userService.findByEmailOrNameAndPassword("li","123");
        User user3 = userService.findByEmailOrNameAndPassword("li", "123");
        System.out.print(user2==user3);
        System.out.println(user == user1);

    }

}
