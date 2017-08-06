package com.lzj.service;

import com.lzj.dao.ArticleDao;
import com.lzj.dao.CommentDao;
import com.lzj.dao.UserDao;
import com.lzj.domain.Article;
import com.lzj.domain.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

/**
 * Created by li on 17-8-6.
 */
@Service
public class CommentService {
    @Autowired
    private CommentDao commentDao;
    @Autowired
    private UserDao userDao;
    @Autowired
    private ArticleDao articleDao;
    @Transactional
    public boolean saveComent(Integer fromUserId,Integer toUserId,String comments,String type,Integer articleId){
        Comment comment=new Comment();
        comment.setFromUser(userDao.getOne(fromUserId));
        comment.setToUser(userDao.getOne(toUserId));
        comment.setUserComent(comments);
        comment.setType(type);
        Article article=articleDao.getOne(articleId);
        Set<Comment>commentSet=article.getComments();
        commentSet.add(comment);
        article=articleDao.save(article);
        if (article.getComments().contains(comment)){
            return true;
        }
        return false;
    }
}
