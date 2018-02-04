package com.lzj.config;

//import com.lzj.interceptor.ClientInterceptor;
import com.lzj.interceptor.ClientInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.converter.MessageConverter;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.session.ExpiringSession;
import org.springframework.session.web.socket.config.annotation.AbstractSessionWebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.AbstractWebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;

import java.beans.Expression;
import java.util.List;

/**https://www.jianshu.com/p/4ef5004a1c81
 *@EnableWebSocketMessageBroker 这个配置类不仅配置了 WebSocket，还配置了基于代理的 STOMP 消息
 * https://segmentfault.com/a/1190000006617344
 */
@Configuration
@EnableScheduling
@EnableWebSocketMessageBroker
public class WebSocketConfig extends AbstractSessionWebSocketMessageBrokerConfigurer<ExpiringSession> {
    @Value("${push.message.topic}")
    String topic;
    @Value("${push.message.queue}")
    String queue;
    @Value("${push.message.prefixe}")
    String prefixe;
    @Value("${push.message.endpoint}")
    String endpoint;
    @Override
    public void configureStompEndpoints(StompEndpointRegistry registry) {
        //允许跨与
        //注册一个Stomp 协议的endpoint,并指定 SockJS ，SockJS是WebSocket 的备选方案。
        // webscoket 有些浏览器有可能不支持这个
        // SockJs 为了应对许多浏览器不支持WebSocket协议的问题，设计了备选SockJs
        // STOMP  面向消息的简单文本协议  STOMP在 WebSocket之上提供了一个基于帧的线路格式层，用来定义消息语义
        /**
         * 1,假设HTTP协议并不存在，只能使用TCP套接字来编写web应用，你可能认为这是一件疯狂的事情。
         2,不过幸好，我们有HTTP协议，它解决了 web 浏览器发起请求以及 web 服务器响应请求的细节。
         3,直接使用 WebSocket（SockJS） 就很类似于 使用 TCP 套接字来编写 web 应用；因为没有高层协议，因此就需要我们定义应用间所发送消息的语义，还需要确保 连接的两端都能遵循这些语义。
         4,同HTTP在TCP套接字上添加请求-响应模型层一样，STOMP在 WebSocket之上提供了一个基于帧的线路格式层，用来定义消息语义。
         */
        registry.addEndpoint(endpoint).setAllowedOrigins("*").withSockJS();
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
    }

    @Override
    public void configureClientOutboundChannel(ChannelRegistration registration) {
        super.configureClientOutboundChannel(registration);
    }

    /**
            *
            * @Title: createUserInterceptor
     * @Description: 将客户端渠道拦截器加入spring ioc容器
     * @return
             */
    /*@Bean
    public ClientInterceptor createUserInterceptor() {
        return new ClientInterceptor();
    }*/
}
