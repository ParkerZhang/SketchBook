package com.example.MQ.MQService;

import com.ibm.mq.jakarta.jms.MQConnectionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerContainerFactory;

@Configuration
public class ListenerConfig {
    @Bean("myFactory")
    public JmsListenerContainerFactory<?> myFactory(MQConnectionFactory connectionFactory) {
        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        System.out.println("Constructing my very own Listener Container Factory!");
        return factory;
    }

}
