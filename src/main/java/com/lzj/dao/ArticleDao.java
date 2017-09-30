package com.lzj.dao;

import com.lzj.domain.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by li on 17-8-6.
 */
public interface ArticleDao {
    //根据文章获取评论
    public Article findArticleById(@Param("id") Integer id);
    public List<Article> getAllByUser_Id(Integer userId);
    void insertArticle(Article article);

}
