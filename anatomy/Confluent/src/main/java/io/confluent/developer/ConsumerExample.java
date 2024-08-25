package io.confluent.developer;

import org.apache.kafka.clients.consumer.*;
//import org.apache.kafka.clients.consumer.Consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.Arrays;
import java.util.Properties;

import static org.apache.kafka.clients.CommonClientConfigs.BOOTSTRAP_SERVERS_CONFIG;
import static org.apache.kafka.clients.CommonClientConfigs.SECURITY_PROTOCOL_CONFIG;
import static org.apache.kafka.clients.consumer.ConsumerConfig.*;
import static org.apache.kafka.common.config.SaslConfigs.SASL_JAAS_CONFIG;
import static org.apache.kafka.common.config.SaslConfigs.SASL_MECHANISM;

public class ConsumerExample {

    public static void main(final String[] args) throws IOException {

//        final Properties props = new Properties() {{
//            // User-specific properties that you must set
//            put(BOOTSTRAP_SERVERS_CONFIG, "<BOOTSTRAP SERVERS>");
//            put(SASL_JAAS_CONFIG,         "org.apache.kafka.common.security.plain.PlainLoginModule required username='<CLUSTER API KEY>' password='<CLUSTER API SECRET>';")
//
//            // Fixed properties
//            put(KEY_DESERIALIZER_CLASS_CONFIG,   StringDeserializer.class.getCanonicalName());
//            put(VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getCanonicalName());
//            put(GROUP_ID_CONFIG,                 "kafka-java-getting-started");
//            put(AUTO_OFFSET_RESET_CONFIG,        "earliest");
//            put(SECURITY_PROTOCOL_CONFIG,        "SASL_SSL");
//            put(SASL_MECHANISM,                  "PLAIN");
//        }};
        final Properties config = readConfig("client.properties");
        config.put(ConsumerConfig.GROUP_ID_CONFIG, "java-group-3");
        config.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        config.put(KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        config.put(VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        final String topic = "ist.swapone.payment";
//        final String topic = "purchases";

        try (final Consumer<String, String> consumer = new KafkaConsumer<>(config)) {
            consumer.subscribe(Arrays.asList(topic));
            while (true) {
                ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));
                for (ConsumerRecord<String, String> record : records) {
                    String key = record.key();
                    String value = record.value();
                    System.out.println(
                            String.format("Consumed event from topic %s: key = %-10s value = %s", topic, key, value));
                }
            }
        }
    }


    public static Properties readConfig(final String configFile) throws IOException {
        // reads the client configuration from client.properties
        // and returns it as a Properties object
        if (!Files.exists(Paths.get(configFile))) {
            throw new IOException(configFile + " not found.");
        }

        final Properties config = new Properties();
        try (InputStream inputStream = new FileInputStream(configFile)) {
            config.load(inputStream);
        }

        return config;
    }

}