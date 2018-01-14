package com.lzj.controller;

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
    @RequestMapping(method = RequestMethod.GET, value = "/operatorFriend")
    public ResponseVO operatorFriend(@RequestBody FriendDto friendDto) {
        Account account = ComentUtils.getCurrentAccount();
        friendDto.setCurrentAccountId(account.getId());
        return friendService.operatorFriend(friendDto);
    }
    @ResponseBody
    @RequestMapping(method = RequestMethod.GET, value = "/deleteFriend")
    public ResponseVO deleteFriend(@RequestBody FriendDto friendDto) {
        Account account = ComentUtils.getCurrentAccount();
        friendDto.setCurrentAccountId(account.getId());
        return friendService.deleteFriend(friendDto);
    }
    @ResponseBody
    @RequestMapping(method = RequestMethod.GET,value = "/groupFriends")
    public ResponseVO<List<Friend>> findGroupFriends(@RequestBody FriendDto dto) {
        Account account = ComentUtils.getCurrentAccount();
        dto.setCurrentAccountId(account.getId());
        return friendService.findGroupFriends(dto);
    }
    @ResponseBody
    @RequestMapping(method = RequestMethod.GET,value = "/get")
    public ResponseVO<Authentication> get() {
        ResponseVO<Authentication> responseVO = new ResponseVO<>();
        responseVO.setResult(SecurityContextHolder.getContext().getAuthentication());
        responseVO.setSuccess(true);
        responseVO.setMessage("lk");
        return responseVO;
    }
    @ResponseBody
    @RequestMapping
    public ResponseVO findSameStatusFriend(@RequestBody FriendDto dto) {
        Account account = ComentUtils.getCurrentAccount();
       return friendService.findSameStatusFriend(dto);
    }
}
