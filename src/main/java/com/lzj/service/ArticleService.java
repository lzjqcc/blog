package com.lzj.service;

import com.lzj.dao.dto.AccountDto;
import com.lzj.domain.*;

import java.util.*;

/**
 * Created by li on 17-8-6.
 */
public interface ArticleService {
    
    void insertArticle(Article article, AccountDto user, String assortment, List<String> picURL);
    Article findById(Integer id);
    List<Article> findAllByUserId(Integer userId,Page page);
    void updateByMap(Map map);
    List<Assortment> findAssortmentByUserId(Integer userId);
    List<Article> specificChildren(Integer userId,String assortment,Page page);
    List<Article>   findHistoryMax(Integer userId, Page page);
    void deleteArticle(Integer id);
    /**
     * 描述：这个月写了几篇博客
     * @param userId
     * @return
     */
    Map<String,List<Article>> findDateNum(Integer userId, Page page);
}

