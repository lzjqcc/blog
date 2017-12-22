package com.lzj.service.impl;

import com.lzj.VO.CommentMongo;
import com.lzj.dao.CommentDao;
import com.lzj.domain.Comment;
import com.lzj.domain.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by li on 17-8-6.
 */
@Service
public class CommentService {
    @Autowired
    CommentDao commentDao;
    @Autowired
    MongoTemplate template;
    public List<CommentMongo> getComments(Integer articleId){
        List<CommentMongo> commentMongos = new ArrayList<>();
       List<Comment> list= commentDao.findByArticleId(articleId);
       for (Comment comment:list){
           if (comment.getReplayComentId()==null){
               CommentMongo mongo=new CommentMongo();
               BeanUtils.copyProperties(comment,mongo);
               List<Comment> children = new ArrayList<>();
               for (Comment child:list){
                   if (comment.getId().equals(child.getReplayComentId())){
                       children.add(child);
                   }
               }
               mongo.setList(children);
               commentMongos.add(mongo);
           }

       }
        return commentMongos;
    }
    public void insertComment(Comment comment){
        commentDao.insertComment(comment);
    }
}
