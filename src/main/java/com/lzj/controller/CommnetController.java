package com.lzj.controller;

import com.lzj.VO.CommentMongo;
import com.lzj.domain.Comment;
import com.lzj.domain.MessageInfo;
import com.lzj.service.ArticleService;
import com.lzj.service.impl.CommentService;
import com.lzj.service.impl.WebScoketService;
import com.lzj.utils.ComentUtils;
import com.lzj.utils.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by li on 17-8-7.
 */
@RestController
@RequestMapping("comment")
public class CommnetController {
    @Autowired
    private CommentService commentService;
    @Autowired
    private ArticleService articleService;
    @Autowired
    WebScoketService webScoketService;
    @RequestMapping(value = "getComments")
    @ResponseBody
    public List<CommentMongo> getComments(@RequestParam("articleId")Integer articleId){
        return commentService.getComments(articleId);
    }
    @ResponseBody
    @RequestMapping(value = "insertComment",method = RequestMethod.POST)
    public void insertComment(@RequestBody Comment comment, HttpServletResponse response, HttpServletRequest request){

        boolean isAccess = ComentUtils.vailedToken(response,request);
        if (!isAccess){
            return;
        }
        commentService.insertComment(comment);
        MessageInfo info = new MessageInfo();
        info.setType(false);
        info.setToUserId(comment.getToUserId());
        info.setFromUserId(comment.getFromUserId());
        info.setContent(comment.getComment());
        info.setFlag(MessageInfo.FLAG.COMMENT_FLAG);
        webScoketService.sendSingleMessageToUser(info);
    }
}
