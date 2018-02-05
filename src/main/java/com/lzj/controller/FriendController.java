package com.lzj.controller;

import com.lzj.VO.GroupFriendVO;
import com.lzj.VO.ResponseVO;
import com.lzj.dao.dto.FriendDto;
import com.lzj.domain.Account;
import com.lzj.domain.Friend;
import com.lzj.service.impl.FriendService;
import com.lzj.utils.ComentUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

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
        return friendService.friendApply(friendDto);
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
        return friendService.operatorFriend(friendDto);
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
    public ResponseVO<List<Integer>> findOnlineFriend() {
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
    public ResponseVO<List<GroupFriendVO>> findCurrentGroupAndFriend() {
        return friendService.getCurrentAccountGroupFriend(ComentUtils.getCurrentAccount());
    }
    @ResponseBody
    @RequestMapping(method = RequestMethod.GET,value = "/get")
    public ResponseVO<Authentication> get() {
        ResponseVO<Authentication> responseVO = new ResponseVO<>();
        responseVO.setResult(SecurityContextHolder.getContext().getAuthentication());
        responseVO.setSuccess(true);
        responseVO.setMessage("0k");
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
