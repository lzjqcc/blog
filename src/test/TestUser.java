import com.lzj.Application;
import com.lzj.dao.AccountDao;
import com.lzj.dao.ArticleDao;
import com.lzj.domain.Account;
import com.lzj.domain.Page;
import com.lzj.service.AccountService;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.transaction.SpringManagedTransaction;
import org.mybatis.spring.transaction.SpringManagedTransactionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by li on 17-8-6.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
@WebAppConfiguration
public class TestUser {
    @Autowired
    private AccountDao userDao;
    @Autowired
    ArticleDao articleDao;
    @Autowired
    SqlSessionTemplate sqlSessionTemplate;
    @Autowired
    DataSource dataSource;
    @Test
    public void testInsert() {
        Account user = new Account();
        user.setUserName("rensdfdfd");
        user.setPassword("12344");
        user.setEmail("43234@qq.com");
       // userDao.insertAccount(user);
        sqlSessionTemplate.insert("insertAccount", user);
    }
    @Autowired
    AccountService userService;

    RedisTemplate redisTemplate;
    // 3 个 redisTemplate  sessionRedisTemplate
    @Autowired
    @Qualifier(value = "sessionRedisTemplate")
    public void setRedisTemplate(RedisTemplate template) {
        System.out.println(template);

    }
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
    public void testfindById() throws SQLException {
        Page page = new Page();
        page.setPageSize(1);
        page.setCurrentPage(1);
        List<Account> list = userDao.findAll(page);
        System.out.print(list.size());
        SpringManagedTransaction transaction = new SpringManagedTransaction(dataSource);
        Connection connection = transaction.getConnection();
        System.out.println(connection+":"+dataSource);
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
