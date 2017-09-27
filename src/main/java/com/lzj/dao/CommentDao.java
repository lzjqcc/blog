package com.lzj.dao;

import com.lzj.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by li on 17-8-6.
 */
public interface CommentDao{
    public List<Comment> findAllByFromUser_Id(Integer userId);
    public List<Comment> findAllByToUser_Id(Integer userId);

}
