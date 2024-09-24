This is a prototype of payment process for swaps.

FileProcessorConfig watches /home/data/in

Forward to FileService

Data Service 
    loads CSV records
    convert to Payment objects
    send Payment objects to Kafka,
    send batch message to Kafka control topic


Create Payment Oboject
    java -jar c:\avro\avro-tools-1.11.3.jar compile schema payment.avsc .
    add CsvBindByName
    add toJson


Todo : to add KafkaTemplate <String, Object> - conflict with <String, String>
        to add IBM MQ beans

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