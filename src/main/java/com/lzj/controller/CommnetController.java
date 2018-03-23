package com.lzj.controller;

import com.google.common.collect.Lists;
import com.lzj.VO.CommentMongo;
import com.lzj.VO.ResponseVO;
import com.lzj.constant.CommentTypeEnum;
import com.lzj.constant.MessageTypeEnum;
import com.lzj.constant.TYPEEnum;
import com.lzj.dao.AccountDao;
import com.lzj.dao.dto.AccountDto;
import com.lzj.domain.Account;
import com.lzj.domain.Comment;
import com.lzj.domain.MessageInfo;
import com.lzj.service.ArticleService;
import com.lzj.service.impl.CommentService;
import com.lzj.service.impl.MessageTemplate;
import com.lzj.service.impl.WebScoketService;
import com.lzj.utils.ComentUtils;
import com.lzj.websocket.WebSocketConstans;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
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
    private AccountDao accountDao;
    @Autowired
    MessageTemplate messageTemplate;
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
        send(comment, ComentUtils.getCurrentAccount());
        /*MessageInfo info = new MessageInfo();
        info.setType(false);
        info.setToAccountId(comment.getToAccountId());
        info.setFromAccountId(comment.getFromAccountId());
        info.setPushMessage(comment.getComment());
        info.setFlag(MessageInfo.FLAG.COMMENT_FLAG);
        webScoketService.sendMessage(info);*/
        return ComentUtils.buildResponseVO(true, "操作成功", comment.getId());
    }

    /**
     * 发送消息
     * @param comment
     * @param account
     */
    private void send(Comment comment, Account account) {
        MessageInfo messageInfo = new MessageInfo();
        messageInfo.setPushMessage(comment.getComment());
        messageInfo.setFlag(MessageTypeEnum.COMMENT.code);
        messageInfo.setType(false);
        messageInfo.setFromAccountId(account.getId());
        messageInfo.setFromAccountName(account.getUserName());
        AccountDto dto = new AccountDto();
        dto.setId(comment.getToAccountId());
        Account toAccount = accountDao.findByDto(dto);
        messageInfo.setToAccountName(toAccount.getUserName());
        messageInfo.setToAccountId(toAccount.getId());
        messageInfo.setCreateTime(comment.getCreateTime());
        messageTemplate.sendToUser(messageInfo,toAccount.getEmail(), WebSocketConstans.NOTIFY_USER_COMMENT, true);
    }
}
