package com.lzj.controller;

import com.lzj.dao.UserDao;
import com.lzj.domain.Article;
import com.lzj.domain.Comment;
import com.lzj.service.ArticleService;

import com.lzj.service.CommentService;
import com.lzj.utils.ComentUtils;
import com.lzj.utils.JsonUtils;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * Created by li on 17-8-6.
 */
@Controller
@RequestMapping("/articles")
public class ArticleController {
    @Autowired
    private ArticleService articleService;
    @Autowired
    private UserDao userDao;
    @Autowired
    private CommentService commentService;

    @RequestMapping(value = "/index")
    public String index() {
        return "index";
    }

    @ResponseBody
    @RequestMapping(value = "/getArticle", method = RequestMethod.GET)
    public Article getArticle(HttpServletRequest request) {
        Integer articleId = Integer.parseInt(request.getParameter("articleId"));
        Article article = null;
        try {
            article = articleService.getArticleById(articleId);
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return article;
    }

    @ResponseBody
    @RequestMapping(value = "/getAllArticles", method = RequestMethod.GET)
    public List<Article> getArticles(HttpServletRequest request) {
        Integer userId = Integer.parseInt(request.getParameter("userId"));

        return articleService.getAllArticlesByUserId(userId);
    }

    /**
     * 保存blog时要指定用户id
     *
     * @param map
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/saveArticle", method = RequestMethod.POST)
    public String saveArticle(@RequestBody Map<String, String> map) {
        Integer userId = Integer.parseInt(map.get("userId"));
        if (userId == null) {
            return "传递userId到后台";
        }
        map.remove("userId");
        JSONObject jsonObject = JSONObject.fromObject(map);
        Article article = (Article) JSONObject.toBean(jsonObject, Article.class);
        article.setCreateTime(ComentUtils.getCurrentTime());
        articleService.saveArticle(userId, article);
        return "success";
    }

    /**
     * 更新文章内容
     *
     * @param map
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String update(@RequestBody Map map) {//map中包含一个评论id数组
        List<Comment> comments = new ArrayList<>();
        List<Integer> list = (List<Integer>) map.get("comments");
        for (Integer id : list) {
            Comment comment = commentService.getCommentById(id);
            comments.add(comment);
        }
        map.remove(comments);
        Article article = JsonUtils.mapToOtherBean(map, Article.class);
        article.setUpdateTime(ComentUtils.getCurrentTime());
        article.setComments(comments);
        articleService.update(article);
        return "success";
    }

    @ResponseBody
    @RequestMapping(value = "/likeOrDisLike", method = RequestMethod.POST)
    public String likeOrDisLike(@RequestBody Map map) throws CloneNotSupportedException {
        int num;
        Integer articleId = Integer.parseInt((String) map.get("articleId"));
        Article article = articleService.getArticleToInnerById(articleId);

        if (map.get("lile") != null) {
            num = Integer.parseInt((String) map.get("like"));
            if (article.getSupport() != null)
                num = article.getSupport() + num;
            article.setSupport(num);
        } else if (map.get("dislike") != null) {
            num = Integer.parseInt((String) map.get("dislike"));
            if (article.getDisLike() != null)
                num = article.getDisLike() + num;
            article.setDisLike(num);
            article.setDisLike(num);
        } else {
            return "fail";
        }
        articleService.update(article);
        return "success";
    }

}
