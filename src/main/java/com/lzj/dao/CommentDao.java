package com.lzj.dao;

import com.lzj.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by li on 17-8-6.
 */
public interface CommentDao extends JpaRepository<Comment,Integer>{
}
