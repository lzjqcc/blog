package com.lzj.controller;

import com.lzj.domain.Comment;
import com.lzj.domain.MessageInfo;
import com.lzj.service.ArticleService;
import com.lzj.service.impl.CommentService;
import com.lzj.service.impl.WebScoketService;
import com.lzj.utils.ComentUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by li on 17-8-7.
 */
@RestController
@RequestMapping("comment")
public class CommnetController {
    private final static Logger log = LoggerFactory.getLogger(CommnetController.class);
    @Autowired
    private CommentService commentService;
    @Autowired
    private ArticleService articleService;
    @Autowired
    WebScoketService webScoketService;

    @ResponseBody
    @RequestMapping(value = "insertComment",method = RequestMethod.POST)
    public void insertComment(@RequestBody Comment comment, HttpServletResponse response, HttpServletRequest request){

      /*  boolean isAccess = ComentUtils.vailedToken(response,request);
        if (!isAccess){
            return;
        }*/
        //commentService.insertComment(comment);
        MessageInfo info = new MessageInfo();
        info.setType(false);
        info.setToAccountId(comment.getToAccountId());
        info.setFromAccountId(comment.getFromAccountId());
        info.setPushMessage(comment.getComment());
        info.setFlag(MessageInfo.FLAG.COMMENT_FLAG);
        webScoketService.sendMessage(info);
    }
}
