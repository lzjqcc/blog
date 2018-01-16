package com.lzj.dao;

import com.lzj.annotation.EnableRelationTable;
import com.lzj.domain.Article;

import com.lzj.domain.LimitCondition;
import com.lzj.domain.Page;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Created by li on 17-8-6.
 */
@Repository("articleDao")
public interface ArticleDao {
    @EnableRelationTable(relationTableName = "tb_friend_function",value = {"current_account_id","friend_id","function_id"})
    void insertArticle(Article article);
    Article findById(Integer id);
    List<Article> findByUserId(@Param("userId") Integer userId, @Param("condition") LimitCondition condition, Page page);
    void deleteById(Integer id);
    void updateArticle(Article article);
    void updateByMap(@Param("map") Map map);
    List<Article> findByUserIdAndAssortment(@Param("userId")Integer userId,
                                            @Param("assortmentId")Integer assortmentId,
                                            @Param("condition")LimitCondition condition);
    /**
     * 描述：获取这个人的文章分类
     * @param user_id
     * @return
     */
    List<Map<String,Object>> findGroupByUserId(Integer user_id);

    /**
     * 描述：获取这个人文章阅读排名
     * @param userId
     * @return
     */
    List<Article>   findHistoryMax(@Param("userId")Integer userId,@Param("condition")LimitCondition condition, Page page);

}
