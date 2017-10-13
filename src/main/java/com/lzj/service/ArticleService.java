package com.lzj.service;

import com.lzj.dao.ArticleDao;
import com.lzj.dao.UserDao;
import com.lzj.domain.*;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * Created by li on 17-8-6.
 */
public interface ArticleService {
    
    void insertArticle(Article article, User user,String assortment,List<String> picURL);
    Article findById(Integer id);
    List<Article> findAllByUserId(Integer userId, LimitCondition condition);
    void updateByMap(Map map);
    List<Assortment> findAssortmentByUserId(Integer userId);
    List<Article> specificChildren(Integer userId,String assortment,LimitCondition condition);
    List<Article>   findHistoryMax(Integer userId,LimitCondition condition);
    void deleteArticle(Integer id);
    /**
     * 描述：这个月写了几篇博客
     * @param userId
     * @return
     */
    Map<String,List<Article>> findDateNum(Integer userId);
}

