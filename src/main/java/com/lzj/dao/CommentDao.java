package com.lzj.dao;

import com.lzj.domain.Comment;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by li on 17-8-6.
 */
@Repository("commentDao")
public interface CommentDao{
    void insertComment(Comment comment);
    List<Comment> findByArticleId(Integer articleId);

    List<Comment> findByPictureGroupId(Integer pictureGroupId);
}
