package com.lzj.controller;

import com.lzj.VO.CommentMongo;
import com.lzj.domain.Comment;
import com.lzj.domain.MessageInfo;
import com.lzj.service.ArticleService;
import com.lzj.service.impl.CommentService;
import com.lzj.service.impl.WebScoketService;
import com.lzj.utils.ComentUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

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
        info.setToAccountId(comment.getToAccountId());
        info.setFromAccountId(comment.getFromAccountId());
        info.setContent(comment.getComment());
        info.setFlag(MessageInfo.FLAG.COMMENT_FLAG);
        webScoketService.sendSingleMessageToUser(info);
    }
}
