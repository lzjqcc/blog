package com.lzj.controller;

import com.lzj.VO.CommentMongo;
import com.lzj.domain.Comment;
import com.lzj.domain.User;
import com.lzj.service.ArticleService;
import com.lzj.service.CommentService;
import com.lzj.utils.ComentUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Set;

/**
 * Created by li on 17-8-7.
 */
@RestController
public class CommnetController {
    @Autowired
    private CommentService commentService;
    @Autowired
    private ArticleService articleService;
    @RequestMapping(value = "getComments")
    @ResponseBody
    public List<CommentMongo> getComments(@RequestParam("articleId")Integer articleId){
        return commentService.getComments(articleId);
    }
    @ResponseBody
    @RequestMapping(value = "insertComment",method = RequestMethod.POST)
    public void insertComment(@RequestBody Comment comment){
        commentService.insertComment(comment);

    }
}
