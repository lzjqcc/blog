package com.lzj.service;

import com.lzj.dao.ArticleDao;
import com.lzj.dao.CommentDao;
import com.lzj.dao.UserDao;
import com.lzj.domain.Article;
import com.lzj.domain.Comment;
import com.lzj.domain.User;
import com.lzj.utils.ComentUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sun.text.resources.no.CollationData_no;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
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

    /**
     * @param fromUserId 哪个人评论
     * @param toUserId   给谁评论
     * @param comments   评论[用户名回复用户名：]
     * @param articleId  评论的文章
     * @return
     */
    @Transactional
    public void saveComent(Integer fromUserId, Integer toUserId, String comments, Integer articleId,Integer commentId) {
        Article article = articleDao.getOne(articleId);
        Comment comment = new Comment();
        User fromUser = userDao.getOne(fromUserId);
        User toUser = userDao.getOne(toUserId);
        comment.setFromUser(fromUser);
        comment.setToUser(toUser);
        comment.setComments(comments);
        if (commentId!=null) {
            Comment parent = commentDao.getOne(commentId);
            comment.setParent(parent);
        }
        List<Comment> commentSet = article.getComments();
        if (commentSet==null){
            commentSet=new ArrayList<>();
        }
        commentSet.add(comment);
        commentDao.save(comment);
        articleDao.save(article);
    }
    @Transactional(readOnly = true)
    public Comment getCommentById(Integer id){
        return  commentDao.getOne(id);
    }

    /**
     * 获取所有登录用户的评论
     * @param userId
     * @return
     */
    public List<Comment> getUserAllComments(Integer userId){
      return   commentDao.findAllByFromUser_Id(userId);

    }

    /**
     * 获取其他用户给自己的评论
     * @return
     */
    public List<Comment> getUserCommentToSelf(Integer userId){
        return commentDao.findAllByToUser_Id(userId);
    }


}
