package com.lzj.dao;

import com.lzj.domain.Comment;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by li on 17-8-6.
 */
@Repository("commentDao")
public interface CommentDao{
    public List<Comment> findAllByFromUser_Id(Integer userId);
    public List<Comment> findAllByToUser_Id(Integer userId);
    void insertComment(Comment comment);
    List<Comment> findByArticleId(Integer articleId);
}
