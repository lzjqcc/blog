package service;

import com.lzj.Application;
import com.lzj.dao.ArticleDao;
import com.lzj.dao.UserDao;
import com.lzj.service.ArticleService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

/**
 * Created by li on 17-8-6.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
public class TestArticleService {
    @Autowired
    private ArticleService articleService;
    @Autowired
    private ArticleDao articleDao;
    @Autowired
    private UserDao userDao;
    @Test
    public void testSave(){
        //这个可以查询到关联实体
        System.out.println(userDao.getOne(1).getArticles());
    }
}
