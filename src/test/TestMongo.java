import com.lzj.Application;
import com.lzj.domain.Article;
import com.lzj.service.impl.ArticleServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
@WebAppConfiguration
public class TestMongo {
    @Autowired
    MongoTemplate template;
    @Autowired
    ArticleServiceImpl impl;
    @Test
    public void testInsert(){
        Article article=new Article();
        article.setContent("正是强 采集" +
                "真实才");
        article.setId(2);
        template.insert(article,"dd");
    }
    @Test
    public void find(){
        Query query = new Query();
        query.addCriteria(Criteria.where("id").is(new Integer(2)));

        Article article=template.findOne(query,Article.class,"dd");
        System.out.println(article.getContent());
    }
    @Test
    public void testPropertis(){
        Query query = new Query();
        query.addCriteria(Criteria.where("id").is(1));
        Update update=new Update();
        update.addToSet("content","lsafj");
    }
}
