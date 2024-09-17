package com.example.mq_spring;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerContainerFactory;
import org.springframework.jms.core.JmsTemplate;

import jakarta.jms.ConnectionFactory;

@Configuration
public class MQConfiguration102 {
    protected final Log logger = LogFactory.getLog(getClass());

    @Autowired
    private ConnectionFactory connectionFactory;

    @Value("${spring.jms.pub-sub-domain:false}")
    public Boolean pubsub;

    @Bean("myPubSubTemplate")
    public JmsTemplate myPubSubJmsTemplate() {
        JmsTemplate jmsTemplate = new JmsTemplate(connectionFactory);
        jmsTemplate.setPubSubDomain(true);
        return jmsTemplate;
    }

    @Bean("myPutGetTemplate")
    public JmsTemplate myPutGetTemplate() {
        JmsTemplate jmsTemplate = new JmsTemplate(connectionFactory);
        jmsTemplate.setPubSubDomain(false);
        return jmsTemplate;
    }


    @Bean
    public JmsListenerContainerFactory<?> mySubContainerFactory102() {
        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setPubSubDomain(true);
        return factory;
    }

    @Bean
    public JmsListenerContainerFactory<?> myGetContainerFactory102() {
        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setPubSubDomain(false);
        logger.info("Jms Get Container Factory 102 Initialized");
        return factory;
    }

    // Need this bean to override default allowing Level 101 components to work in conjunction
    // with subsequent components
    @Bean("jmsTemplate")
    public JmsTemplate jmsTemplate() {
        //return new JmsTemplate(connectionFactory);
        JmsTemplate jmsTemplate = new JmsTemplate(connectionFactory);
        if (pubsub) {
            jmsTemplate.setPubSubDomain(true);
        } else {
            jmsTemplate.setPubSubDomain(false);
        }
        return jmsTemplate;
    }

}
