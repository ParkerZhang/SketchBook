Reference: https://www.confluent.io/hub/confluentinc/kafka-connect-ibmmq?session_ref=https%3A%2F%2Fwww.confluent.io%2Fwhat-is-apache-kafka%2F%3Futm_medium%3Dsem%26utm_source%3Dgoogle%26utm_campaign%3Dch.sem_br.nonbrand_tp.prs_tgt.kafka_mt.xct_rgn.namer_sbrgn.canada_lng.eng_dv.all_con.kafka-general%26utm_term%3Dapache%2520kafka%26creative%3D%26device%3Dc%26placement%3D%26gad_source%3D1%26gclid%3DCjwKCAiAxea5BhBeEiwAh4t5K_w_xTiLaGkuPF9S3p03NkWFYybI0Bw1z4QqDxXezNr0bP_kps7CLxoCLS0QAvD_BwE

Install CP:
    https://www.confluent.io/installation/

Start Confluent
    cd /home/app/confluent-7.7.1
    source set-confluent.sc
        zookeeper-server-start ${CONFLUENT_HOME}/etc/kafka/zookeeper.properties
        kafka-server-start ${CONFLUENT_HOME}/etc/kafka/server.properties
        schema-registry-start ${CONFLUENT_HOME}/etc/schema-registry/schema-registry.properties
        control-center-start ${CONFLUENT_HOME}/etc/confluent-control-center/control-center.properties
        connect-distributed ${CONFLUENT_HOME}/etc/schema-registry/connect-avro-distributed.properties
        kafka-rest-start ${CONFLUENT_HOME}/etc/kafka-rest/kafka-rest.properties
        ksql-server-start ${CONFLUENT_HOME}/etc/ksqldb/ksql-server.properties

Uninstall
    rm -rf confluent-7.7.1
    rm -rf /var/lib/<confluent-platform-data-files>

With Docker
    One Composer File
        https://github.com/confluentinc/cp-all-in-one/blob/7.7.1-post/cp-all-in-one-kraft/docker-compose.yml

        wget https://raw.githubusercontent.com/confluentinc/cp-all-in-one/7.7.1-post/cp-all-in-one-kraft/docker-compose.yml
        docker-compose up -d
        docker-compose ps

                    Name                    Command               State                               Ports
--------------------------------------------------------------------------------------------------------------
broker            /etc/confluent/docker/run        Up      0.0.0.0:9092->9092/tcp,:::9092->9092/tcp,
0.0.0.0:9101->9101/tcp,:::9101->9101/tcp
connect           /etc/confluent/docker/run        Up      0.0.0.0:8083->8083/tcp,:::8083->8083/tcp, 9092/tcp
control-center    /etc/confluent/docker/run        Up      0.0.0.0:9021->9021/tcp,:::9021->9021/tcp
ksql-datagen      bash -c echo Waiting for K ...   Up
ksqldb-cli        /bin/sh                          Up
ksqldb-server     /etc/confluent/docker/run        Up      0.0.0.0:8088->8088/tcp,:::8088->8088/tcp
rest-proxy        /etc/confluent/docker/run        Up      0.0.0.0:8082->8082/tcp,:::8082->8082/tcp
schema-registry   /etc/confluent/docker/run        Up      0.0.0.0:8081->8081/tcp,:::8081->8081/tcp


        docker-compose restart control-center

    Step 2: Create Kafka topics for storing your data
        (https://docs.confluent.io/platform/current/get-started/platform-quickstart.html)
        http://localhost:9021
            Click the controlcenter.cluster tile.