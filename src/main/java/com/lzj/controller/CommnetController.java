package com.lzj.controller;

import com.lzj.VO.CommentMongo;
import com.lzj.VO.ResponseVO;
import com.lzj.constant.CommentTypeEnum;
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
import java.util.List;

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
    @RequestMapping(value = "getComments/{articleId}", method = RequestMethod.GET)
    public ResponseVO getComments(@PathVariable Integer articleId) {
        List<CommentMongo> list = commentService.getComments(articleId, CommentTypeEnum.ARTICLE.code);
        return ComentUtils.buildResponseVO(true, "操作成功", list);
    }
    @ResponseBody
    @RequestMapping(value = "insertComment",method = RequestMethod.POST)
    public ResponseVO insertComment(@RequestBody Comment comment){

      /*  boolean isAccess = ComentUtils.vailedToken(response,request);
        if (!isAccess){
            return;
        }*/
        comment.setFromAccountId(ComentUtils.getCurrentAccount().getId());
        comment.setCurrentAccountId(ComentUtils.getCurrentAccount().getId());
        commentService.insertComment(comment);
        /*MessageInfo info = new MessageInfo();
        info.setType(false);
        info.setToAccountId(comment.getToAccountId());
        info.setFromAccountId(comment.getFromAccountId());
        info.setPushMessage(comment.getComment());
        info.setFlag(MessageInfo.FLAG.COMMENT_FLAG);
        webScoketService.sendMessage(info);*/
        return ComentUtils.buildResponseVO(true, "操作成功", comment.getId());
    }
}
