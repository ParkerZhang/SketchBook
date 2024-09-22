package org.example.kafkaapplication;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.*;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaConfig {

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;

    @Value("${spring.kafka.security.protocol}")
    private String securityProtocol;

    @Value("${spring.kafka.properties.sasl.mechanism}")
    private String saslMechanism;

    @Value("${spring.kafka.properties.acks}")
    private String acks;
    @Value("${spring.kafka.properties.sasl.jaas.config}")
    private String jaasConfig;

    @Value("${spring.kafka.properties.jaas.username:#{null}}")
    private String username;

    @Value("${spring.kafka.properties.jaas.password:#{null}}")
    private String password;
    final KafkaSecret secret;

    public KafkaConfig(KafkaSecret secret) {
        this.secret = secret;
    }

    @Bean
    public ProducerFactory<String, String> producerFactory() {
        String replacedJaas;
        if (secret.getUsername() != null) {
            replacedJaas = jaasConfig.replace("{it}", secret.getUsername()).replace("{pwd}", secret.getPassword());

        } else {
            replacedJaas = jaasConfig.replace("{it}", username).replace("{pwd}", password);
        }
        Map<String, Object> configProps = new HashMap<>();
        configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        configProps.put("security.protocol", securityProtocol);
        configProps.put("sasl.mechanism", saslMechanism);
        configProps.put("sasl.jaas.config", replacedJaas);
        configProps.put("acks", acks);
        return new DefaultKafkaProducerFactory<>(configProps);
    }

    @Bean
    public KafkaTemplate<String, String> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }


    @Bean
    public ConsumerFactory<String, String> consumerFactory() {
        String replacedJaas;
        if (secret.getUsername() != null) {
            replacedJaas = jaasConfig.replace("{it}", secret.getUsername()).replace("{pwd}", secret.getPassword());

        } else {
            replacedJaas = jaasConfig.replace("{it}", username).replace("{pwd}", password);
        }
        Map<String, Object> configProps = new HashMap<>();
        configProps.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        configProps.put(ConsumerConfig.GROUP_ID_CONFIG, "my-group");
        configProps.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        configProps.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        configProps.put("security.protocol", securityProtocol);
        configProps.put("sasl.mechanism", saslMechanism);
        configProps.put("sasl.jaas.config", replacedJaas);
        configProps.put("acks", acks);
        return new DefaultKafkaConsumerFactory<>(configProps);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, String> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, String> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        return factory;
    }
}
