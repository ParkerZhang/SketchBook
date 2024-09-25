package com.example.MQ.MQService;

import com.ibm.mq.jakarta.jms.MQConnectionFactory;
import com.ibm.msg.client.wmq.WMQConstants;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.core.JmsTemplate;


@Configuration
@ConfigurationProperties(prefix = "mq")
@Getter
@Setter
public class JmsConfig {
    private String HOSTNAME;
    private int PORT;
    private String QUEUE_MANAGER;
    private String CHANNEL_NAME;
    private String USER;
    private String PASSWORD;
    private static Logger logger = LoggerFactory.getLogger(JmsConfig.class);

    @PostConstruct
    public void init() {
        System.setProperty("com.ibm.mq.cfg.useIBMCipherMappings", "false");
    }

    // With or without the following bean,
    // JmsTemplate injects the following MQconnectionFactory
    @Bean
    public JmsTemplate getJmsTemplate() throws Exception {
        JmsTemplate jmsTemplate = new JmsTemplate();
        jmsTemplate.setConnectionFactory(connectionFactory());

        System.out.println("Constructing my very own JmsTemplate");
        return jmsTemplate;
    }

    @Bean
//  @DependsOn("SslFactory")
    public MQConnectionFactory connectionFactory() throws Exception {
//      MQQueueConnectionFactory connectionFactory = new MQQueueConnectionFactory();
        MQConnectionFactory connectionFactory = new MQConnectionFactory(); //?? vs ^
        connectionFactory.setHostName(HOSTNAME);
        connectionFactory.setPort(PORT);
        connectionFactory.setQueueManager(QUEUE_MANAGER);
        connectionFactory.setChannel(CHANNEL_NAME);
        connectionFactory.setTransportType(WMQConstants.WMQ_CM_CLIENT);
        connectionFactory.setStringProperty(WMQConstants.USERID, USER);
        connectionFactory.setStringProperty(WMQConstants.PASSWORD, PASSWORD);

//      connectionFactory.setSSLSocketFactory(SslFactory.getSSLSocketFactory());
//      connectionFactory.setSSLCipherSuite(sslCipherSuite);
        System.out.println("Constructing my very own connectionFactory.");
        return connectionFactory;
    }
}