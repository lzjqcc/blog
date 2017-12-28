package com.lzj.controller;

import com.lzj.VO.ResponseVO;
import com.lzj.dao.dto.FriendDto;
import com.lzj.domain.Account;
import com.lzj.domain.Friend;
import com.lzj.service.impl.FriendService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

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
    public  ResponseVO addFriend(@RequestBody FriendDto friendDto, HttpSession session) {
        Account account = (Account) session.getAttribute("user");
        friendDto.setCurrentAccountId(account.getId());
        return friendService.friendApply(friendDto);
    }

    /**
     * 更新好友操作
     * @param friendDto
     * @return
     */
    @ResponseBody
    @RequestMapping(method = RequestMethod.GET, value = "/operatorFriend")
    public ResponseVO operatorFriend(@RequestBody FriendDto friendDto, HttpSession session) {
        Account account = (Account) session.getAttribute("user");
        friendDto.setCurrentAccountId(account.getId());
        return friendService.operatorFriend(friendDto);
    }
    @ResponseBody
    @RequestMapping(method = RequestMethod.GET, value = "/deleteFriend")
    public ResponseVO deleteFriend(@RequestBody FriendDto friendDto, HttpSession session) {
        Account account = (Account) session.getAttribute("user");
        friendDto.setCurrentAccountId(account.getId());
        return friendService.deleteFriend(friendDto);
    }
    @ResponseBody
    @RequestMapping(method = RequestMethod.GET,value = "/groupFriends")
    public List<Friend> findGroupFriends(@RequestBody FriendDto dto,HttpSession session) {
        Account account = (Account) session.getAttribute("user");
        dto.setCurrentAccountId(account.getId());
        return friendService.findGroupFriends(dto);
    }
}
