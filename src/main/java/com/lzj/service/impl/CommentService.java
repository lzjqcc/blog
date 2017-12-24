package com.lzj.service.impl;

import com.lzj.VO.CommentMongo;
import com.lzj.constant.CommentTypeEnum;
import com.lzj.dao.CommentDao;
import com.lzj.domain.Comment;
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

    /**
     *
     * @param typeId articleId or pictureGroupId
     * @param type @see CommentTypeEnum
     * @return
     */
    public List<CommentMongo> getComments(Integer typeId, int type){
        List<CommentMongo> commentMongos = new ArrayList<>();
        List<Comment> list = null;
        if (type == CommentTypeEnum.ARTICLE.code) {
            list= commentDao.findByArticleId(typeId);
        }else if (type == CommentTypeEnum.PICTRUE.code) {
            list = commentDao.findByPictureGroupId(typeId);
        }

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
