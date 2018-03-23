package com.lzj.controller;

import com.lzj.VO.ResponseVO;
import com.lzj.constant.FriendStatusEnum;
import com.lzj.constant.MessageTypeEnum;
import com.lzj.dao.dto.FriendDto;
import com.lzj.domain.Account;
import com.lzj.domain.Friend;
import com.lzj.security.AccountToken;
import com.lzj.service.impl.FriendService;
import com.lzj.utils.ComentUtils;
import com.lzj.websocket.WebSocketConstans;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/friend")
public class FriendController {
    private final static Logger log = LoggerFactory.getLogger(FriendController.class);
    @Autowired
    private FriendService friendService;

    /**
     * 好友申请
     * @param friendDto
     * @return
     */
    @ResponseBody
    @RequestMapping(method = RequestMethod.POST,value = "/applyFriend")
    public  ResponseVO addFriend(@RequestBody FriendDto friendDto) {
        SecurityContext context = SecurityContextHolder.getContext();
        System.out.print(context.getAuthentication());

        Account account = ComentUtils.getCurrentAccount();
        friendDto.setCurrentAccountId(account.getId());
        ResponseVO responseVO = friendService.friendApply(friendDto);
        friendService.send(account, friendDto, MessageTypeEnum.FRIEND_APPLY.code, WebSocketConstans.NOTIFY_FRIEND_APPLY);
        return responseVO;
    }

    /**
     * 更新好友操作
     * @param friendDto
     * @return
     */
    @ResponseBody
    @RequestMapping(method = RequestMethod.POST, value = "/operatorFriend")
    public ResponseVO operatorFriend(@RequestBody FriendDto friendDto) {
        Account account = ComentUtils.getCurrentAccount();
        friendDto.setCurrentAccountId(account.getId());
        ResponseVO  responseVO = friendService.operatorFriend(friendDto);
        if (friendDto.getStatus() != null && friendDto.getStatus().intValue() == FriendStatusEnum.AGREE.code.intValue()) {
            friendService.send(account, friendDto, MessageTypeEnum.FRIEND_AGREE.code, WebSocketConstans.NOTIFY_FRIEND_AGREE);
        }
        return responseVO;
    }
    @ResponseBody
    @RequestMapping(method = RequestMethod.GET, value = "/deleteFriend")
    public ResponseVO deleteFriend(@RequestParam("friendId") Integer friendId) {
        Account account = ComentUtils.getCurrentAccount();
        FriendDto friendDto = new FriendDto();
        friendDto.setCurrentAccountId(account.getId());
        friendDto.setFriendId(friendId);
        return friendService.deleteFriend(friendDto);
    }
    @ResponseBody
    @RequestMapping(method = RequestMethod.GET,value = "/findAllFriends")
    public ResponseVO<List<Friend>> findAllFriends() {
        return friendService.findAllFriends(ComentUtils.getCurrentAccount());
    }
    /**
     * 查找在线好友
     * @return
     */
    @ResponseBody
    @RequestMapping(method = RequestMethod.GET, value = "/findOnlineFriends")
    public ResponseVO<List<Friend>> findOnlineFriend() {
        Account account = ComentUtils.getCurrentAccount();
        return friendService.findOnlineFriends(account.getId());
    }
    @ResponseBody
    @RequestMapping(method = RequestMethod.GET,value = "/groupFriends")
    public ResponseVO<List<Friend>> findGroupFriends(@RequestParam("groupId") Integer groupId) {
        FriendDto friendDto = new FriendDto();
        Account account = ComentUtils.getCurrentAccount();
        friendDto.setCurrentAccountId(account.getId());
        friendDto.setGroupId(groupId);
        return friendService.findGroupFriends(friendDto);
    }
    @ResponseBody
    @RequestMapping(method = RequestMethod.GET, value = "/currentGroupAndFriend")
    public ResponseVO findCurrentGroupAndFriend() {
        Map<String, Object> map = new HashMap<>();
        map.put("currentAccount", ComentUtils.getCurrentAccount());
        map.put("group", friendService.getCurrentAccountGroupFriend(ComentUtils.getCurrentAccount()).getResult());
        return ComentUtils.buildResponseVO(true, "操纵成功", map);
    }
    @ResponseBody
    @RequestMapping(method = RequestMethod.GET,value = "/get")
    public ResponseVO<Account> get() {
        ResponseVO<Account> responseVO = new ResponseVO<>();
        AccountToken token = (AccountToken) SecurityContextHolder.getContext().getAuthentication();
        responseVO.setResult(token.getAccount());
        responseVO.setSuccess(true);
        responseVO.setMessage("ok");
        return responseVO;
    }
    @ResponseBody
    @RequestMapping(method = RequestMethod.GET, value = "findSameStatusFriend")
    public ResponseVO findSameStatusFriend(@RequestParam("status") Integer status,
                                            @RequestParam("friendId") Integer friendId) {
        Account account = ComentUtils.getCurrentAccount();
        FriendDto dto = new FriendDto();
        dto.setCurrentAccountId(account.getId());
        dto.setFriendId(friendId);
        dto.setStatus(status);
       return friendService.findSameStatusFriend(dto);
    }
}
