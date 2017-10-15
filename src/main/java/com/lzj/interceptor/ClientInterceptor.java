package com.lzj.interceptor;

import com.lzj.domain.Client;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptorAdapter;
import org.springframework.messaging.support.MessageHeaderAccessor;

import java.util.LinkedList;
import java.util.Map;

/**
 *拦截前端的操作：连接，订阅，发送消息.....
 */
public class ClientInterceptor  extends ChannelInterceptorAdapter{
    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {

        StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
        //比较当前状态
        if (StompCommand.CONNECT.equals(accessor.getCommand())){
            //获取消息请求头
            Object raw = message.getHeaders().get(SimpMessageHeaderAccessor.NATIVE_HEADERS);
            if (raw instanceof Map){
                //获取键=name的消息头
               Object id= ((Map)raw).get("id");
               if (id instanceof LinkedList){
                   accessor.setUser(new Client(((LinkedList) id).get(0).toString()));
               }
            }
        }
        return message;
    }

}
