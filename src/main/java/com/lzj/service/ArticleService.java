package com.lzj.service;

import com.lzj.dao.ArticleDao;
import com.lzj.dao.UserDao;
import com.lzj.domain.Article;
import com.lzj.domain.Comment;
import com.lzj.domain.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.collection.internal.PersistentSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.*;

/**
 * Created by li on 17-8-6.
 */
@Service

public class ArticleService {
    private ArticleDao articleDao;
    @Autowired
    private UserDao userDao;
    @Autowired
    private EntityManager entityManager;

    /**
     * 保存article,并建立登录的user与article关联
     *
     * @param userId
     * @param article
     */
    @Transactional
    public void saveArticle(Integer userId, Article article) {
/*        User user = userDao.getOne(userId);
        article.setUser(user);
        articleDao.save(article);*/
    }
    //给内部调用，用于更新
    @Transactional
    public Article getArticleToInnerById(Integer id){
        return null;
    }
    //这是给前端调用的
    @Transactional(readOnly = true)
    public Article getArticleById(Integer id) throws CloneNotSupportedException {
        Article article = articleDao.findArticleById(id);
        List<Comment> commentSet = article.getComments();
        List<Comment> list = null;
        for (Comment comment : commentSet) {
            Comment second = null;
            list = new ArrayList<>();
            for (Comment parent : commentSet) {
                second = parent;
                if (comment.getParent() != null && comment.getParent().getId() != null && comment.getParent().getId().equals(second.getId())) {
                    second = parent;
                    if (second.getChildren() != null) {
                        second.getChildren().add(comment);
                    } else {
                        list.add(comment);
                        parent.setChildren(list);
                    }

                }
            }
        }

        Iterator<Comment> iterable = commentSet.iterator();
        while (iterable.hasNext()) {
            Comment comment = iterable.next();
            if (comment.getParent() != null)//有父亲的都放到list中了
                iterable.remove();//这个remove只是将数据冲set中拿掉
        }
        return article;
    }

    @Transactional(readOnly = true)
    public List<Article> getAllArticlesByUserId(Integer userId) {
        return articleDao.getAllByUser_Id(userId);
    }

    @Transactional
    //Could not obtain transaction-synchronized Session for current thread 表示session要在事务环境进行
    public void update(Article article) {


        SessionFactory sessionFactory = entityManager.getEntityManagerFactory().unwrap(SessionFactory.class);

        Session session = sessionFactory.getCurrentSession();
        session.merge(article);

    }
}
