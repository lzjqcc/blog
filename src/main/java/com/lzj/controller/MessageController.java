package com.lzj.controller;

import com.lzj.dao.MessageDao;
import com.lzj.domain.MessageInfo;
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
    MessageDao messageDao;
    @Value("${push.message.queuemessage}")
    String queueMessage;
    @RequestMapping(value = "dd")
    @ResponseBody
    public void sendSpecificMessageToUser(
            @RequestParam("id") Integer id,@RequestBody MessageInfo info){
        for (SimpUser user:userRegistry.getUsers()){
            if (user.getName().equals(id)){
                template.convertAndSendToUser(id+"",queueMessage,info);
                break;
            }
        }

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
