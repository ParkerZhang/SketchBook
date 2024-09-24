package org.example.kafkaapplication.VaultService;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("kafka")
@Getter
@Setter
public class KafkaSecret {

        private String username;
        private String password;


}
