import com.lzj.Application;
import com.lzj.VO.CommentMongo;
import com.lzj.dao.ArticleDao;
import com.lzj.domain.Article;
import com.lzj.domain.Comment;
import com.lzj.domain.Page;
import com.lzj.service.ArticleService;
import com.lzj.service.impl.CommentService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
@WebAppConfiguration
public class TestArticle {
    @Autowired
    ArticleDao articleDao;
    @Autowired
    CommentService commentService;
    @Test
    public void testComment() {
      List<CommentMongo> list =  commentService.getComments(21, 0);
      int i=0;
    }
    @Test
    public void testInsertComment() {
        Comment comment = new Comment();
        comment.setCurrentAccountId(4);
        comment.setFromAccountId(4);
        comment.setToAccountId(2);
        comment.setArticleId(21);
        comment.setComment("你最菜了");
        commentService.insertComment(comment);

    }
    @Test
    public void testFindById(){
        List<Article> list  = articleDao.findGroupByCreateTime(2);
        int i = 0;
    }
    @Test
    public void testInsert(){
        Article article = new Article();
        article.setContent("dlfkj");
        article.setTitle("任命");
        articleDao.insertArticle(article);

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
        Page page = new Page();
        page.setCurrentPage(1);
        page.setPageSize(1);
        Map<String,List<Article>> map=articleService.findDateNum(2, page);
        Set<Map.Entry<String,List<Article>>> set=map.entrySet();
        for (Map.Entry<String,List<Article>> entry:set){
            System.out.println(entry.getKey()+":"+entry.getValue().size());
        }
    }
/*    @Test
    public void testInsert(){
        Article article=new Article();
        article.setTitle("真实");
        article.setPushMessage("真实男上来看打机");
        User user=new User();
        user.setId(1);
        article.setUserId(1);
        articleService.insertArticle(article,user,"散文",null);

    }*/
}
