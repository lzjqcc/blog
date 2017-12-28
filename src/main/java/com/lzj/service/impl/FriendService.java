package com.lzj.service.impl;

import com.google.common.collect.Lists;
import com.lzj.VO.ResponseVO;
import com.lzj.constant.AuthorityEnum;
import com.lzj.constant.FriendStatusEnum;
import com.lzj.controller.FriendController;
import com.lzj.dao.FriendDao;
import com.lzj.dao.FunctionDao;
import com.lzj.dao.GroupDao;
import com.lzj.dao.GroupFriendDao;
import com.lzj.dao.dto.FriendDto;
import com.lzj.dao.dto.FunctionDto;
import com.lzj.domain.Account;
import com.lzj.domain.Friend;
import com.lzj.domain.Group;
import com.lzj.domain.GroupFriend;
import com.lzj.exception.SystemException;
import com.lzj.utils.ValidatorUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.ValidationUtils;

import java.util.List;

@Service
public class FriendService {
    private final static Logger log = LoggerFactory.getLogger(FriendService.class);
    @Autowired
    private FriendDao friendDao;
    @Autowired
    private FunctionDao functionDao;
    @Autowired
    private GroupFriendDao groupFriendDao;
    @Autowired
    private GroupDao groupDao;
    public List<Friend> findGroupFriends(FriendDto dto) {
        ResponseVO responseVO = ValidatorUtils.validatorData(dto);
        if (!responseVO.getSuccess()) {
            return null;
        }
        return friendDao.findGroupFriendsByDto(dto);
    }
    public ResponseVO deleteFriend(FriendDto dto) {
        ResponseVO responseVO = ValidatorUtils.validatorData(dto);
        if (!responseVO.getSuccess()) {
            return responseVO;
        }
        friendDao.deleteFriend(dto);
        responseVO.setSuccess(true);
        responseVO.setMessage("删除成功");
        return responseVO;
    }

    /**
     * @param dto
     * @return
     */
    @Transactional
    public ResponseVO friendApply(FriendDto dto) {
        ResponseVO responseVO = ValidatorUtils.validatorData(dto);
        if (!responseVO.getSuccess()) {
            return responseVO;
        }
        try {
            insertFriend(dto);
            responseVO.setSuccess(true);
            responseVO.setMessage("好友申请成功");
            return responseVO;
        } catch (SystemException e) {
            e.setMessage(e.getMessage() + "    好友申请失败");
            throw e;
        }

    }


    /**
     * currentAccountId friendId 确定一条记录
     *
     * @param dto
     */
    public ResponseVO operatorFriend(FriendDto dto) {
        ResponseVO responseVO = ValidatorUtils.validatorData(dto);
        if (!responseVO.getSuccess()) {
            return responseVO;
        }
        ResponseVO vo = new ResponseVO();
        // 同意
        if (dto.getStatus().intValue() == FriendStatusEnum.AGREE.code.intValue()) {
            if (dto.getStatus() == null) {
                vo.setSuccess(false);
                vo.setMessage("没有处理好友申请");
                return vo;
            }
            if (dto.getGroupId() == null) {
                vo.setSuccess(false);
                vo.setMessage("没有好友分组");
                return vo;
            }
            friendDao.updateFriend(dto);
            insertGroupFriend(dto);
        }
        //拒绝,拉黑,关注
        if (dto.getStatus().intValue() == FriendStatusEnum.REFUSE.code.intValue() ||
                dto.getDefriend() != null || dto.getSpecialAttention() != null) {
            friendDao.updateFriend(dto);
        }
        //更新好友在哪个分组
        if (dto.getStatus() == null && dto.getGroupId() != null) {
            groupFriendDao.updateGroupFriend(dto);
        }
        if (dto.getFriendName() != null) {
            groupFriendDao.updateGroupFriend(dto);
        }
        vo.setSuccess(true);
        vo.setMessage("操作成功");
        return vo;
    }

    private void insertFriend(FriendDto dto) {
        log.info("开始添加好友friendId={},当前添加人currentAccountId={},groupId={}", dto.getFriendId(), dto.getCurrentAccountId(), dto.getGroupId());
        Friend friend = new Friend();
        friend.setCurrentAccountId(dto.getCurrentAccountId());
        friend.setFriendId(dto.getFriendId());
        friend.setFunctionList(Lists.newArrayList(AuthorityEnum.PICTURE_GROUP_SEE.id, AuthorityEnum.PICTURE_GROUP_COMMENT.id));
        friend.setStatus(FriendStatusEnum.APPLE.code);
        friendDao.insertFriend(friend);
        friend.setFriendName(dto.getFriendName());
        log.info("添加成功");
    }

    private void insertGroupFriend(FriendDto dto) {
        GroupFriend groupFriend = new GroupFriend();
        groupFriend.setCurrentAccountId(dto.getCurrentAccountId());
        groupFriend.setFriendId(dto.getFriendId());
        groupFriend.setGroupId(dto.getGroupId());
        groupFriendDao.insertGroupFriend(groupFriend);
    }
}
