package com.lzj.controller;

import com.lzj.annotation.CurrentUser;
import com.lzj.dao.MessageDao;
import com.lzj.dao.dto.AccountDto;
import com.lzj.domain.Account;
import com.lzj.domain.MessageInfo;
import com.lzj.security.AccountToken;
import com.lzj.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.messaging.simp.user.SimpUser;
import org.springframework.messaging.simp.user.SimpUserRegistry;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/message")
public class MessageController {
    @Autowired
    SimpUserRegistry userRegistry;
    @Autowired
    SimpMessagingTemplate template;
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
        template.convertAndSendToUser(account.getEmail(),queue+"messages", info);

    }
    @RequestMapping(value = "cc")
    public void  send() {
        Map<String, String> map = new HashMap<>();
        map.put("dd", "dd");
            template.convertAndSend("/topic/top",map);
    }
    @RequestMapping(value = "toRead/{id}",method = RequestMethod.GET )
    @ResponseBody
    public void toRead(@PathVariable Integer id){
        messageDao.updateType(true,id);
    }
    public List<MessageInfo> getMessageByFlagAndType(){
        return null;
    }
}
