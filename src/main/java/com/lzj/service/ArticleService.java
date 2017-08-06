package com.lzj.service;

import com.lzj.dao.ArticleDao;
import com.lzj.dao.UserDao;
import com.lzj.domain.Article;
import com.lzj.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

/**
 * Created by li on 17-8-6.
 */
@Service

public class ArticleService {
    @Autowired
    private ArticleDao articleDao;
    @Autowired
    private UserDao userDao;

    /**
     * 保存article,并建立登录的user与article关联
     * @param userId
     * @param article
     */
    @Transactional
    public boolean saveArticle(Integer userId,Article article){
        User user=userDao.getOne(userId);
        Set<Article>articles=user.getArticles();
        article=articleDao.save(article);
        articles.add(article);
        user.setArticles(articles);
        user=userDao.save(user);
        if (user.getArticles().contains(article)){
            return true;
        }else {
            return false;
        }
    }
}
