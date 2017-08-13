package com.lzj.controller;

import com.lzj.domain.Comment;
import com.lzj.domain.User;
import com.lzj.service.ArticleService;
import com.lzj.service.CommentService;
import com.lzj.utils.ComentUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
    @RequestMapping("/comments")
    public List<Comment> getComments(@RequestParam("articleId")Integer articleId) throws CloneNotSupportedException {

        return articleService.getArticleById(articleId).getComments();
    }
    @RequestMapping("/saveComments")
    public String saveComments(@RequestParam("fromUserId") Integer fromUserId,@RequestParam("toUserID")Integer toUserId
            ,@RequestParam("comment")String comment,@RequestParam("articleId") Integer articleId,@RequestParam("parentId")Integer parentId){
        commentService.saveComent(fromUserId,toUserId,comment,articleId,parentId);
        return "success";
    }

    /**
     * 获取登录用户的所有评论
     * @return
     */
    public List<Comment> getUserAllComments(HttpSession session){
        User user= (User) session.getAttribute("user");

        return commentService.getUserAllComments(user.getId());
    }

    /**
     * 获取给自己的评论
     * @param session
     * @return
     */
    public List<Comment> getUserAllCommentsToSelf(HttpSession session){
        User user= (User) session.getAttribute("user");
        return commentService.getUserCommentToSelf(user.getId());
    }

}
