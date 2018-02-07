package com.lzj.service.impl;

import com.google.common.collect.Lists;
import com.lzj.VO.GroupFriendVO;
import com.lzj.VO.ResponseVO;
import com.lzj.constant.AuthorityEnum;
import com.lzj.constant.FriendStatusEnum;
import com.lzj.controller.FriendController;
import com.lzj.dao.FriendDao;
import com.lzj.dao.FunctionDao;
import com.lzj.dao.GroupDao;
import com.lzj.dao.dto.FriendDto;
import com.lzj.dao.dto.FunctionDto;
import com.lzj.dao.dto.GroupDto;
import com.lzj.domain.*;
import com.lzj.exception.SystemException;
import com.lzj.helper.RedisTemplateHelper;
import com.lzj.utils.ComentUtils;
import com.lzj.utils.ValidatorUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.ValidationUtils;

import java.util.ArrayList;
import java.util.List;

@Service
public class FriendService {
    private final static Logger log = LoggerFactory.getLogger(FriendService.class);
    @Autowired
    private FriendDao friendDao;
    @Autowired
    private FunctionDao functionDao;

    @Autowired
    private GroupDao groupDao;
    @Autowired
    RedisTemplateHelper redisTemplateHelper;
    public ResponseVO<List<Friend>> findAllFriends(Account currentAccount) {
        FriendDto dto = new FriendDto();
        dto.setCurrentAccountId(currentAccount.getId());
        dto.setStatus(FriendStatusEnum.AGREE.code);

        List<Friend> list = friendDao.findFriends(dto);
        return ComentUtils.buildResponseVO(true, "操作成功", list);
    }
    /**
     * 获取当前登陆人的 好友分组与分组中的好友
     * @return
     */
    public ResponseVO<List<GroupFriendVO>> getCurrentAccountGroupFriend(Account currentAccount) {
        GroupDto groupDto = new GroupDto();
        groupDto.setCurrentAccountId(currentAccount.getId());
        List<Group> list =groupDao.findGroupsByDto(groupDto);
        List<GroupFriendVO> groupFriendVOS = new ArrayList<>();
        for (Group group : list) {
            FriendDto friendDto = new FriendDto();
            friendDto.setGroupId(group.getId());
            friendDto.setCurrentAccountId(currentAccount.getId());
            List<Friend> friends = friendDao.findFriends(friendDto);
            GroupFriendVO groupFriendVO = new GroupFriendVO();
            groupFriendVO.setFriends(friends);
            GroupDto dto = new GroupDto();
            dto.setCurrentAccountId(currentAccount.getId());
            dto.setGroupName(group.getGroupName());
            dto.setId(group.getId());
            groupFriendVO.setGroupDto(dto);
        }
        return ComentUtils.buildResponseVO(true, "操作成功", groupFriendVOS);
    }
    /**
     * currentAccountId
     * groupId
     * @param dto
     * @return
     */
    public ResponseVO<List<Friend>> findGroupFriends(FriendDto dto) {
        ResponseVO<List<Friend>> responseVO = ValidatorUtils.validatorData(dto);
        if (!responseVO.getSuccess()) {
            return null;
        }
        responseVO.setResult(friendDao.findFriends(dto));
        return responseVO;
    }

    /**
     * 删除好友 A删除B
     * 在数据库中 删除两条记录    currentAccountId = currentAccountId ,   friendId = currentAccountId
     * 删除 tb_group_friend 中两条相应的记录
     * @param dto
     * @return
     */
    public ResponseVO deleteFriend(FriendDto dto) {
        ResponseVO responseVO = ValidatorUtils.validatorData(dto);
        if (!responseVO.getSuccess()) {
            return responseVO;
        }
        friendDao.deleteFriend(dto);
        FriendDto s = new FriendDto();
        s.setFriendId(dto.getCurrentAccountId());
        s.setCurrentAccountId(dto.getFriendId());
        friendDao.deleteFriend(s);
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
        if (dto.getGroupId() == null) {
            return ComentUtils.buildResponseVO(false, "请选择好友分组", null);
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
     * 查找好友
     * 1,申请状态的好友
     * friendId = currentAccountId status = APPLY
     * 2, 自己的申请状态
     * currentAccountId = currentAccountId status = APPLY
     * @Param dto currentAccountId 为当前登陆人
     * @return
     */
    public ResponseVO<List<Friend>> findSameStatusFriend(FriendDto dto) {
        if (dto.getStatus() !=null ) {
            FriendDto friendDto = new FriendDto();
            BeanUtils.copyProperties(dto,friendDto);
            friendDto.setFriendId(dto.getCurrentAccountId());
            dto = friendDto;
        }

        return ComentUtils.buildResponseVO(true, "操作成功",  friendDao.findFriends(dto));
    }
    public ResponseVO<List<Friend>> findOnlineFriends(Integer currentAccountId) {
        FriendDto dto = new FriendDto();
        dto.setCurrentAccountId(currentAccountId);
        dto.setStatus(FriendStatusEnum.AGREE.code);
        List<Friend> list = friendDao.findFriends(dto);
        List<Friend> ids = new ArrayList<>();
        for (Friend friend : list) {
            if (redisTemplateHelper.get(friend.getFriendId()+"") != null) {
                ids.add(friend);
            }
        }
        return ComentUtils.buildResponseVO(true, "操作成功", ids);
    }
    /**
     * currentAccountId friendId 确定一条记录
     *
     * @param dto currentAccountId 为当前登陆人
     */
    public ResponseVO operatorFriend(FriendDto dto) {
        ResponseVO responseVO = ValidatorUtils.validatorData(dto);
        if (!responseVO.getSuccess()) {
            return responseVO;
        }
        ResponseVO vo = new ResponseVO();
        // 同意
        if (dto.getStatus().intValue() == FriendStatusEnum.AGREE.code.intValue()) {
            if (dto.getGroupId() == null) {
                vo.setSuccess(false);
                vo.setMessage("没有好友分组");
                return vo;
            }
            //同意好友申请 B申请好友 A (currentAccountId = Bid,friendId = Aid ,status = Apply),
            // A同意 更新上面那条记录status = Agree并且插入一条 currentAccountId = AId, friendId = Bid, status=AGREE
            FriendDto friendDto = new FriendDto();
            BeanUtils.copyProperties(dto, friendDto);
            friendDto.setFriendId(dto.getCurrentAccountId());
            friendDao.updateFriend(friendDto);
            insertFriend(dto);
        }
        //拒绝,拉黑,关注
        if (dto.getStatus().intValue() == FriendStatusEnum.REFUSE.code.intValue() ||
                dto.getDefriend() != null || dto.getSpecialAttention() != null) {
            friendDao.updateFriend(dto);
        }
        //更新好友在哪个分组
        if (dto.getStatus() == null && dto.getGroupId() != null) {
            friendDao.updateFriend(dto);
        }
        if (dto.getFriendName() != null) {
            friendDao.updateFriend(dto);
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
        friend.setFunctionList(Lists.newArrayList(AuthorityEnum.FRIEND_PICTURE_GROUP_SEE.id, AuthorityEnum.FRIEND_PICTURE_GROUP_COMMENT.id));
        friend.setStatus(dto.getStatus());
        friend.setFriendName(dto.getFriendName());
        friendDao.insertFriend(friend);
        log.info("添加成功");
    }

}
