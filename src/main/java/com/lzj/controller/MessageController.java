package com.lzj.controller;

import com.lzj.VO.ResponseVO;
import com.lzj.annotation.CurrentUser;
import com.lzj.constant.MessageTypeEnum;
import com.lzj.dao.MessageDao;
import com.lzj.dao.dto.AccountDto;
import com.lzj.dao.dto.MessageDto;
import com.lzj.domain.Account;
import com.lzj.domain.MessageInfo;
import com.lzj.security.AccountToken;
import com.lzj.service.AccountService;
import com.lzj.service.impl.MessageTemplate;
import com.lzj.utils.ComentUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.messaging.simp.user.SimpUser;
import org.springframework.messaging.simp.user.SimpUserRegistry;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/message")
public class MessageController {
    @Autowired
    SimpUserRegistry userRegistry;
    @Autowired
    MessageTemplate template;
    @Autowired
    AccountService accountService;
    @Autowired
    MessageDao messageDao;
    @Value("${push.message.queuemessage}")
    String queueMessage;
    @Value("${push.message.queue}")
    String queue;
    // 有关 user的解释https://stackoverflow.com/questions/37853727/where-user-comes-from-in-convertandsendtouser-works-in-sockjsspring-websocket
    @MessageMapping(value = "/chat")
    public void sendSpecificMessageToUser(@RequestBody MessageInfo info, @CurrentUser AccountToken accountToken){
        AccountDto accountDto = new AccountDto();
        accountDto.setId(info.getFriendId());

        Account account = accountService.findByDto(accountDto);
        info.setFromAccountId(accountToken.getAccount().getId());
        template.sendToUser(info,account.getEmail(),queue+"messages", false);

    }
    /**
     * 已读消息
     * @return
     */
    @RequestMapping(value = "/findAllRead")
    public ResponseVO<List<MessageInfo>> findAllReadMessage(@RequestParam("flag") Integer flag) {
        MessageDto messageDto = new MessageDto();
        messageDto.setToAccountId(ComentUtils.getCurrentAccount().getId());
        messageDto.setFlag(flag);
        messageDto.setType(true);
        List<MessageInfo> list = messageDao.findMessagesByDto(messageDto);

        return ComentUtils.buildResponseVO(true, "操作成功", list);
    }

    /**
     * 未读消息
     * @return
     */
    @RequestMapping(value = "/findAllUnRead")
    public ResponseVO<List<MessageInfo>> findAllUnReadMessage(@RequestParam("flag") Integer flag) {
        MessageDto messageDto = new MessageDto();
        messageDto.setToAccountId(ComentUtils.getCurrentAccount().getId());
        messageDto.setType(false);
        messageDto.setFlag(flag);
        List<MessageInfo> list = messageDao.findMessagesByDto(messageDto);
        if (!CollectionUtils.isEmpty(list) && flag == MessageTypeEnum.FRIEND_AGREE.code.intValue()) {
            List<Integer> ids = new ArrayList<>();
            list.stream().forEach(t -> {
                    ids.add(t.getId());
            });
            messageDao.updateType(true, ids);
        }
        return ComentUtils.buildResponseVO(true, "操作成功", messageDao.findMessagesByDto(messageDto));
    }
    @RequestMapping(value = "/remove")
    public ResponseVO remove(@RequestParam("messageId") Integer messageId) {
        messageDao.deleteMessageInfo(messageId);
        return ComentUtils.buildResponseVO(true,"操作成功", null);
    }



}
