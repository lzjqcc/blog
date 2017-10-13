import com.lzj.Application;
import com.lzj.dao.ArticleDao;
import com.lzj.domain.Article;
import com.lzj.domain.User;
import com.lzj.service.ArticleService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
public class TestArticle {
    @Autowired
    ArticleDao articleDao;
    @Test
    public void testFindById(){
       Article article= articleService.findById(6);
       System.out.println(article);
    }
    @Test
    public void testUpdateByMap(){
        Map<String,Object> map=new HashMap<>();
        map.put("id",1);
        map.put("title","lidd");
        map.put("description","a");
        articleDao.updateByMap(map);
    }
    @Test
    public void testFindGroup(){
        List<Map<String,Object>> list=articleDao.findGroupByUserId(1);
        for (Map<String,Object> map:list){
            System.out.println(map);
        }
    }
    @Autowired
    ArticleService articleService;
    @Test
    public void testFindDateNum(){
        Map<String,List<Article>> map=articleService.findDateNum(1);
        Set<Map.Entry<String,List<Article>>> set=map.entrySet();
        for (Map.Entry<String,List<Article>> entry:set){
            System.out.println(entry.getKey()+":"+entry.getValue().size());
        }
    }
    @Test
    public void testInsert(){
        Article article=new Article();
        article.setTitle("真实");
        article.setContent("真实男上来看打飞机");
        User user=new User();
        user.setId(1);
        article.setUserId(1);
        articleService.insertArticle(article,user,"散文",null);

    }
}
