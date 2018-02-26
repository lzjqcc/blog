package com.lzj.service.impl;

import com.lzj.VO.CommentMongo;
import com.lzj.constant.CommentTypeEnum;
import com.lzj.dao.AccountDao;
import com.lzj.dao.CommentDao;
import com.lzj.domain.Account;
import com.lzj.domain.Comment;
import com.lzj.utils.ComentUtils;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.partitioningBy;

/**
 * Created by li on 17-8-6.
 */
@Service
public class CommentService {
    @Autowired
    CommentDao commentDao;
    @Autowired
    MongoTemplate template;
    @Autowired
    AccountDao accountDao;

    private void buildName() {

    }

    /**
     * @param typeId articleId or pictureGroupId
     * @param type   @see CommentTypeEnum
     * @return
     */
    public List<CommentMongo> getComments(Integer typeId, int type) {
        List<CommentMongo> commentMongos = new ArrayList<>();
        List<Comment> list = null;
        if (type == CommentTypeEnum.ARTICLE.code) {
            list = commentDao.findByArticleId(typeId);
        } else if (type == CommentTypeEnum.PICTRUE.code) {
            list = commentDao.findByPictureGroupId(typeId);
        }
        if (CollectionUtils.isEmpty(list)) {
            return new ArrayList<>();
        }
        List<Integer> fromAccountIds = new ArrayList<>();
        List<Integer> toAccountIds = new ArrayList<>();
        list.stream().forEach(t -> fromAccountIds.add(t.getFromAccountId()));
        list.stream().forEach(t -> toAccountIds.add(t.getToAccountId()));
        Set<Integer> set = new HashSet<>(fromAccountIds);
        set.addAll(toAccountIds);
        List<Integer> accountIds = set.stream().collect(Collectors.toList());
        List<Account> accounts = accountDao.findAccountsByIds(accountIds);
        Map<Integer, Account> map = accounts.stream().collect(Collectors.toMap(Account::getId, t -> t));
        Map<Boolean, List<Comment>> comments = list.stream().collect(partitioningBy(e -> e.getReplayComentId() != null));
        List<Comment> replayCommentNotNull = comments.get(true);
        List<Comment> replayCommentNull = comments.get(false);
        for (Comment comment : replayCommentNull) {
            CommentMongo mongo = new CommentMongo();
            BeanUtils.copyProperties(comment, mongo);
            mongo.setToAccountName(map.get(comment.getToAccountId()).getUserName());
            mongo.setFromAccountName(map.get(comment.getFromAccountId()).getUserName());
            mongo.setSrc(ComentUtils.getImageURL(map.get(comment.getFromAccountId()).getHeadIcon()));
            List<Comment> children = new ArrayList<>();
            Comment preComment = comment;
            for (Comment child : replayCommentNotNull) {
                if (preComment.getId().equals(child.getReplayComentId())) {
                    child.setFromAccountName(map.get(child.getFromAccountId()).getUserName());
                    child.setToAccountName(map.get(child.getToAccountId()).getUserName());
                    child.setSrc(ComentUtils.getImageURL(map.get(child.getFromAccountId()).getHeadIcon()));
                    children.add(child);
                    preComment = child;
                }
            }
            if (CollectionUtils.isNotEmpty(children)) {
                mongo.setList(children);
            }
            commentMongos.add(mongo);
        }
        return commentMongos;
    }

    public void insertComment(Comment comment) {
        commentDao.insertComment(comment);
    }
    private void getChild(List<Comment> list) {

    }
}
