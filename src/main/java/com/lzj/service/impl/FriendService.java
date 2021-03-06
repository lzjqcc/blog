package com.lzj.service.impl;

import com.google.common.collect.Lists;
import com.lzj.VO.GroupFriendVO;
import com.lzj.VO.ResponseVO;
import com.lzj.constant.AuthorityEnum;
import com.lzj.constant.FriendStatusEnum;
import com.lzj.constant.MessageTypeEnum;
import com.lzj.controller.FriendController;
import com.lzj.dao.*;
import com.lzj.dao.dto.*;
import com.lzj.domain.*;
import com.lzj.exception.SystemException;
import com.lzj.helper.RedisTemplateHelper;
import com.lzj.helper.TransactionHelper;
import com.lzj.utils.ComentUtils;
import com.lzj.utils.ValidatorUtils;
import com.lzj.websocket.WebSocketConstans;
import org.apache.commons.collections.CollectionUtils;
import org.mybatis.spring.transaction.SpringManagedTransaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.ValidationUtils;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class FriendService {
    private final static Logger log = LoggerFactory.getLogger(FriendService.class);
    @Autowired
    private FriendDao friendDao;
    @Autowired
    private FunctionDao functionDao;
    @Autowired
    private AccountDao accountDao;
    @Autowired
    private GroupDao groupDao;
    @Autowired
    RedisTemplateHelper redisTemplateHelper;
    @Autowired
    MessageTemplate messageTemplate;
    @Autowired
    TransactionHelper transactionHelper;
    @Autowired
    MessageDao messageDao;
    public FriendService() {

    }
    public FriendService(String a) {
        System.out.println(a);
    }
    public ResponseVO<List<AuthPrictureDto>> findFriendsAuth(Integer accountId) {
        List<Friend> friends = findAllFriends(accountId).getResult();
        List<AuthPrictureDto> authPrictureDtos = new ArrayList<>();
        if (CollectionUtils.isEmpty(friends)) {
            return ComentUtils.buildResponseVO(true, "操作成功", authPrictureDtos);
        }
        List<FriendFunction> functions = functionDao.findAllFriendFuntions(accountId);
        Map<Integer, List<FriendFunction>> map = new HashMap<>();
        for (FriendFunction friendFunction: functions) {
            if (map.get(friendFunction.getFriendId()) == null) {
                List<FriendFunction> list = new ArrayList<>();
                list.add(friendFunction);
                map.put(friendFunction.getFriendId(), list);
            }else {
                map.get(friendFunction.getFriendId()).add(friendFunction);
            }
        }
        for (Friend friend : friends) {
           List<FriendFunction> friendFunctions = map.get(friend.getId());
           authPrictureDtos.add(buildAuthPrictureDto(friend, friendFunctions));
        }
        return ComentUtils.buildResponseVO(true, "操作成功", authPrictureDtos);
    }
    public AuthPrictureDto buildAuthPrictureDto(Friend friend, List<FriendFunction> friendFunctions) {
        AuthPrictureDto dto = new AuthPrictureDto();
        dto.setFriendName(friend.getFriendName());
        dto.setId(friend.getId());
        if (CollectionUtils.isEmpty(friendFunctions)) {
            return dto;
        }
        friendFunctions.stream().forEach(t-> {
            if (t.getAuth().equals(AuthorityEnum.FRIEND_PICTURE_GROUP_SEE.authority)) {
                dto.setLookPictureAuth(true);
            }
            if (t.getAuth().equals(AuthorityEnum.FRIEND_PICTURE_GROUP_COMMENT.authority)) {
                dto.setCommentPictureAuth(true);
            }
        });
        return dto;
    }
    public ResponseVO<List<Friend>> findAllFriends(Integer currentAccountId) {
        FriendDto dto = new FriendDto();
        dto.setCurrentAccountId(currentAccountId);
        dto.setStatus(FriendStatusEnum.AGREE.code);

        List<Friend> list = friendDao.findFriends(dto);
        return ComentUtils.buildResponseVO(true, "操作成功", list);
    }

    /**
     * 获取当前登陆人的 好友分组与分组中的好友
     *
     * @return
     */
    public ResponseVO<List<GroupFriendVO>> getCurrentAccountGroupFriend(Account currentAccount) {
        GroupDto groupDto = new GroupDto();
        groupDto.setCurrentAccountId(currentAccount.getId());
        List<Group> list = groupDao.findGroupsByDto(groupDto);
        List<GroupFriendVO> groupFriendVOS = new ArrayList<>();
        List<Integer> accountIds = new ArrayList<>();
        findAllFriends(currentAccount.getId()).getResult().stream().forEach(f -> {
            accountIds.add(f.getFriendId());
        });
        if (CollectionUtils.isEmpty(accountIds)) {
            for (Group group : list) {
                GroupFriendVO groupFriendVO = new GroupFriendVO();
                groupFriendVO.setGroupDto(buildGroupDto(group));
                groupFriendVOS.add(groupFriendVO);
            }
            return ComentUtils.buildResponseVO(true, "操作成功", groupFriendVOS);
        }
        Map<Integer, Account> map = accountDao.findAccountsByIds(accountIds).stream().collect(Collectors.toMap(Account::getId, t -> t));
        List<Friend> onlineFriends = findOnlineFriends(currentAccount.getId()).getResult();
        Map<Integer, Friend> onlineMap = onlineFriends.stream().collect(Collectors.toMap(Friend::getId, f -> f));
        for (Group group : list) {
            FriendDto friendDto = new FriendDto();
            friendDto.setGroupId(group.getId());
            friendDto.setStatus(FriendStatusEnum.AGREE.code);
            friendDto.setCurrentAccountId(currentAccount.getId());
            List<Friend> friends = friendDao.findFriends(friendDto);
            friends.stream().forEach( f-> {
                f.setHeadIcon(map.get(f.getFriendId()).getHeadIcon());
                f.setPersonalSignature(map.get(f.getFriendId()).getPersonalSignature());
                if (onlineMap.get(f.getId()) != null) {
                    f.setOnline(true);
                }
            });
            GroupFriendVO groupFriendVO = new GroupFriendVO();
            groupFriendVO.setFriends(friends);
            groupFriendVO.setGroupDto(buildGroupDto(group));
            groupFriendVOS.add(groupFriendVO);
        }
        return ComentUtils.buildResponseVO(true, "操作成功", groupFriendVOS);
    }
    private GroupDto buildGroupDto(Group group) {
        GroupDto dto = new GroupDto();
        dto.setCurrentAccountId(group.getCurrentAccountId());
        dto.setGroupName(group.getGroupName());
        dto.setId(group.getId());
        return dto;

    }
    /**
     * currentAccountId
     * groupId
     *
     * @param dto
     * @return
     */
    public ResponseVO<List<Friend>> findGroupFriends(FriendDto dto) {
        ResponseVO<List<Friend>> responseVO = ValidatorUtils.validatorData(dto);
        if (!responseVO.getSuccess()) {
            return responseVO;
        }
        responseVO.setResult(friendDao.findFriends(dto));
        return responseVO;
    }

    /**
     * 删除好友 A删除B
     * 在数据库中 删除两条记录    currentAccountId = currentAccountId ,   friendId = currentAccountId
     * 删除 tb_group_friend 中两条相应的记录
     *
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
        TransactionStatus status  = transactionHelper.beginTransaction();
        try {
            dto.setStatus(FriendStatusEnum.APPLE.code);
            FriendDto query = new FriendDto();
            query.setFriendId(dto.getFriendId());
            responseVO.setSuccess(true);
            responseVO.setMessage("好友申请成功");
            query.setCurrentAccountId(dto.getCurrentAccountId());
            List<Friend> friends = friendDao.findFriends(query);
            if (CollectionUtils.isNotEmpty(friends)) {
                log.info("更新好友申请 currentAccountId={}, friendId={},groupId={}", dto.getCurrentAccountId(),dto.getFriendId(),dto.getGroupId());
                friendDao.updateFriend(dto);
                transactionHelper.commit(status);
                return responseVO;
            }
            insertFriend(dto);
            transactionHelper.commit(status);
        } catch (SystemException e) {
            e.setMessage(e.getMessage() + "    好友申请失败");
            transactionHelper.rollback(status);
            throw e;
        }
        send(ComentUtils.getCurrentAccount(), dto, MessageTypeEnum.FRIEND_APPLY.code, WebSocketConstans.NOTIFY_FRIEND_APPLY);
        return responseVO;

    }

    /**
     * 发送好友相关请消息
     * @param fromAccount
     * @param dto
     */
    public void send(Account fromAccount, FriendDto dto, int messageFlag, String dest) {
        MessageInfo messageInfo  = new MessageInfo();
        messageInfo.setFromAccountName(fromAccount.getUserName());
        messageInfo.setFromAccountId(fromAccount.getId());
        messageInfo.setToAccountId(dto.getFriendId());
        messageInfo.setToAccountName(dto.getFriendName());
        messageInfo.setType(false);
        messageInfo.setFlag(messageFlag);
        messageInfo.setCreateTime(new Date());
        if (messageFlag == MessageTypeEnum.FRIEND_AGREE.code.intValue()) {
            messageInfo.setPushMessage(fromAccount.getUserName() + "同意加你为好友");
        }
        if (messageFlag == MessageTypeEnum.FRIEND_APPLY.code.intValue()) {
            messageInfo.setPushMessage(fromAccount.getUserName()+" 申请加你为好友");
        }
        AccountDto dto1 = new AccountDto();
        dto1.setId(dto.getFriendId());
        Account toAccount = accountDao.findByDto(dto1);

        messageTemplate.sendToUser(messageInfo, toAccount.getEmail(), dest, true);
    }

    /**
     * 查找好友
     * 1,申请状态的好友
     * friendId = currentAccountId status = APPLY
     * 2, 自己的申请状态
     * currentAccountId = currentAccountId status = APPLY
     *
     * @return
     * @Param dto currentAccountId 为当前登陆人
     */
    public ResponseVO<List<Friend>> findSameStatusFriend(FriendDto dto) {
        if (dto.getStatus() != null) {
            FriendDto friendDto = new FriendDto();
            BeanUtils.copyProperties(dto, friendDto);
            friendDto.setFriendId(dto.getCurrentAccountId());
            dto = friendDto;
        }

        return ComentUtils.buildResponseVO(true, "操作成功", friendDao.findFriends(dto));
    }

    public ResponseVO<List<Friend>> findOnlineFriends(Integer currentAccountId) {
        FriendDto dto = new FriendDto();
        dto.setCurrentAccountId(currentAccountId);
        dto.setStatus(FriendStatusEnum.AGREE.code);
        List<Friend> list = friendDao.findFriends(dto);
        List<Friend> ids = new ArrayList<>();
        for (Friend friend : list) {
            if (redisTemplateHelper.get(friend.getFriendId() + "") != null) {
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
        ResponseVO vo = new ResponseVO();
        if (dto.getFriendId() == null) {
            vo.setSuccess(false);
            vo.setMessage("没有指定好友");
            return vo;
        }
        // 同意
        if (dto.getStatus() != null && dto.getStatus().intValue() == FriendStatusEnum.AGREE.code.intValue()) {
            if (dto.getGroupId() == null) {
                vo.setSuccess(false);
                vo.setMessage("没有好友分组");
                return vo;
            }
            //同意好友申请 B申请好友 A (currentAccountId = Bid,friendId = Aid ,status = Apply),
            // A同意 更新上面那条记录status = Agree并且插入一条 currentAccountId = AId, friendId = Bid, status=AGREE
            FriendDto friendDto = new FriendDto();
            friendDto.setFriendId(dto.getCurrentAccountId());
            friendDto.setCurrentAccountId(dto.getFriendId());
            friendDto.setStatus(FriendStatusEnum.AGREE.code);
            friendDao.updateFriend(friendDto);
            insertFriend(dto);
            vo.setSuccess(true);
            vo.setMessage("操作成功");
            dto.setFriendName(accountDao.findAccountsByIds(Lists.newArrayList(dto.getFriendId())).get(0).getUserName());
            send(ComentUtils.getCurrentAccount(), dto, MessageTypeEnum.FRIEND_AGREE.code, WebSocketConstans.NOTIFY_FRIEND_AGREE);
            if (dto.getMessageId() != null) {
                messageDao.updateType(true, Lists.newArrayList(dto.getMessageId()));
            }
            return vo;
        }
        if (dto.getStatus() != null && dto.getStatus().intValue() == FriendStatusEnum.REFUSE.code.intValue()) {
            dto.setCurrentAccountId(dto.getFriendId());
            dto.setFriendId(ComentUtils.getCurrentAccount().getId());
            friendDao.updateFriend(dto);
            vo.setSuccess(true);
            vo.setMessage("操作成功");
            return vo;
        }
        friendDao.updateFriend(dto);
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
        friend.setGroupId(dto.getGroupId());
        friendDao.insertFriend(friend);
        log.info("添加成功");
    }

}
