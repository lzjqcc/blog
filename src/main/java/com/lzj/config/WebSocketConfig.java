package com.lzj.config;

import com.lzj.interceptor.ClientInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.converter.MessageConverter;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.AbstractWebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;

import java.util.List;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig extends AbstractWebSocketMessageBrokerConfigurer {
    @Value("${push.message.topic}")
    String topic;
    @Value("${push.message.queue}")
    String queue;
    @Value("${push.message.prefixe}")
    String prefixe;
    @Value("${push.message.endpoint}")
    String endpoint;
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        //允许跨与
        registry.addEndpoint(endpoint).setAllowedOrigins("*").withSockJS();//注册一个Stomp 协议的endpoint,并指定 SockJS协议。
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        //订阅
        registry.enableSimpleBroker(topic,queue); //广播式应配置一个/topic 消息代理
        // 全局使用的消息前缀（客户端订阅路径上会体现出来）
        registry.setApplicationDestinationPrefixes(prefixe);
        // 点对点使用的订阅前缀（客户端订阅路径上会体现出来），不设置的话，默认也是/user/
        // registry.setUserDestinationPrefix("/user/");
    }
    /**
     * 配置客户端入站通道拦截器
     */
    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.setInterceptors(createUserInterceptor());
    }

    /**
     *
     * @Title: createUserInterceptor
     * @Description: 将客户端渠道拦截器加入spring ioc容器
     * @return
     */
    @Bean
    public ClientInterceptor createUserInterceptor() {
        return new ClientInterceptor();
    }
}
