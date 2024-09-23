This is a sample code that uses Spring Kafka to produce and consume messages
    Producer uses KafkaTemplate 
    Consumer uses KafkaLinstener

the send a message  :   

        http://127.0.0.1:8080/send?message=your_message


Using HashCorp Vault
        Startup Vault
            local_run_vault.bat
        Set Secret
            set_secret.bat
secret will ovwerride properties
    spring.kafka.properties.jaas.username=xxxx
    spring.kafka.properties.jaas.password=xxxx

https://confluent.cloud/environments/env-w1drdj/clusters/lkc-2yj601/topics/my-topic/overview