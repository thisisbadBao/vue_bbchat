package com.bbchat.redis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.stereotype.Component;

@Component
public class RedisListenerBean {

    private static final Logger LOGGER = LoggerFactory.getLogger(RedisListenerBean.class);

    @Value("${server.port}")
    private String serverPort;

    @Value("${redis.channel.msgToAll}")
    private String msgToAll;

    @Bean
    RedisMessageListenerContainer container(RedisConnectionFactory connectionFactory, MessageListenerAdapter listenerAdapter){
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);

        container.addMessageListener(listenerAdapter,new PatternTopic(msgToAll));
        LOGGER.info("Subscribed Redis channel: "+ msgToAll);
        return container;
    }
}
