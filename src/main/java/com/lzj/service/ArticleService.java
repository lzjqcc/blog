package com.lzj.service;

import com.lzj.VO.ArticleIndexVO;
import com.lzj.VO.PageVO;
import com.lzj.VO.ResponseVO;
import com.lzj.dao.dto.AccountDto;
import com.lzj.dao.dto.ArticleDto;
import com.lzj.domain.*;

import java.util.*;

/**
 * Created by li on 17-8-6.
 */
public interface ArticleService {
    
    void insertArticle(Article article, AccountDto user, String assortment, List<String> picURL);
    Article findById(Integer id);
    PageVO<List<ArticleIndexVO>> findAllByUserId(Integer assortmentId,Integer userId,Page page);
    void updateByMap(Map map);
    void  updateArticle(ArticleDto dto);
    List<Assortment> findAssortmentByUserId(Integer userId);
    PageVO<List<ArticleIndexVO>> findByCreateDesc(String searchKey, Page page);
    List<Article> specificChildren(Integer userId,String assortment,Page page);
    List<Article>   findHistoryMax(Integer userId, Page page);
    void deleteArticle(Integer id);
    /**
     * 描述：这个月写了几篇博客
     * @param userId
     * @return
     */
    Map<String,List<Article>> findDateNum(Integer userId, Page page);
    ResponseVO<Map<String, Integer>> findGroupByCreateTime(Integer userId);
}

