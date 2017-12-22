package com.lzj.service.impl;

import com.lzj.dao.MessageDao;
import com.lzj.domain.MessageInfo;
import com.lzj.exception.SystemException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.user.SimpUserRegistry;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.*;

@Service
public class WebScoketService {
    @Autowired
    SimpUserRegistry userRegistry;
    @Autowired
    SimpMessagingTemplate template;
    @Value("${push.message.queuemessage}")
    String queueMessage;
    @Value("${push.message.topicmessage}")
    String topicMessage;
    @Autowired
    MessageDao messageDao;
    private static ThreadPoolExecutor executor;

    static {
        BlockingQueue queue = new ArrayBlockingQueue(10);
        executor = new ThreadPoolExecutor(10, 15, 2, TimeUnit.MINUTES, queue);
    }

    /**
     * 发送广播消息
     *
     * @param message
     */
    public void sendMessage(final MessageInfo message) {
        executor.execute(()->{
            template.convertAndSend(topicMessage,message);
        });
    }

    /**
     * 发送信息到指定用户
     *
     * @param messageInfo
     */
    public void sendSingleMessageToUser(final MessageInfo messageInfo) {
        executor.execute(() -> {
            try {
                template.convertAndSendToUser(messageInfo.getToAccountId() + "", queueMessage, messageInfo);
                messageDao.insertMessage(messageInfo);
            } catch (Exception e) {
                throw new SystemException(246, messageInfo.getToAccountId() + "消息发送失败" + messageInfo.getFromAccountId(), this.getClass().getSimpleName(), "sendMessageToUser", e.toString());
            }
        });

    }

    /**
     * 发送用户未读信息
     * @param list
     */
    public void sendNotReadMessageToUser(List<MessageInfo> list) {
        if (list!=null && list.size()>0){
            MessageInfo info=list.get(0);
            executor.execute(() -> {
                template.convertAndSendToUser(info.getToAccountId().toString(),queueMessage,list);
            });
        }

    }
}
