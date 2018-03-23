package com.lzj.service.impl;

import com.lzj.dao.MessageDao;
import com.lzj.domain.MessageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
public class MessageTemplate {
    @Autowired
    SimpMessagingTemplate template;
    @Autowired
    MessageDao messageDao;
    public void send(MessageInfo messageInfo,String destination, boolean save) {
        if (save) {
            messageDao.insertMessage(messageInfo);
        }
        template.convertAndSend(destination, messageInfo);
    }
    public void sendToUser(MessageInfo messageInfo, String user,String destination, boolean save) {
        if (save) {
            messageDao.insertMessage(messageInfo);
        }
        template.convertAndSendToUser(user,destination, messageInfo);
    }
}
