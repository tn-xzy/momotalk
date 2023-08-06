package com.momotalk_v1.config;


import cn.dev33.satoken.stp.StpUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

import java.security.Principal;
import java.util.List;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
//        registry.addEndpoint("/ws").setAllowedOriginPatterns("*").withSockJS();
        registry.addEndpoint("/ws").setAllowedOriginPatterns("*");
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.enableSimpleBroker("/forward");
//        registry.setApplicationDestinationPrefixes("/talk");
    }

//    @Override
//    public void configureClientInboundChannel(ChannelRegistration registration) {
//        registration.interceptors(new ChannelInterceptor() {
//            @Override
//            public Message<?> preSend(Message<?> message, MessageChannel channel) {
//                System.out.println("logined");
//                System.out.println("logined"+StpUtil.isLogin());
//                StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
//                //1、判断是否首次连接
//                if (accessor != null && StompCommand.CONNECT.equals(accessor.getCommand())) {
//                    //2、判断token
////                    if (StpUtil.isLogin()) {
////                        Principal principal = new Principal() {
////                            @Override
////                            public String getName() {
////                                return StpUtil.getSession().getString("username");
////                            }
////                        };
////                        accessor.setUser(principal);
////                        return message;
////                    }
//                    System.out.println("logined"+StpUtil.isLogin());
//                    StpUtil.checkLogin();
//                    return null;
//                }
//                //不是首次连接，已经登陆成功
//                return message;
//            }
//        });
////        WebSocketMessageBrokerConfigurer.super.configureClientInboundChannel(registration);
//    }
}
