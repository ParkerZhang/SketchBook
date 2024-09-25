===============Copilot version=======================
The project aims to demonstrate how to set up MQ with Docker and connect to MQ using JmsTemplate, with or without a custom MQConnectionFactory. The MQConnectionFactory utilizes MQ connection properties such as server name, port, and SSL Cipher Suite. The default ConnectionFactory does not support SSLContext with IBM MQ using TLS_RSA_WITH_AES_256_CBC_SHA256 and a keystore. Therefore, jakarta.jms.MQConnectionFactory is used to inject SSLSocketFactory, although this line is commented out in the code.

There are two listeners running:

One from MessageConsumer, using a specific Listener Container Factory called “myFactory”.
Another in the main class, using an auto-wired JmsTemplate, which ultimately uses “myFactory”.
This setup is similar to the Sender in both the HeartBeat and the main class.

=====================================================
============== original writing======================
The project is to demonstrate how to setup MQ with docker
and connect to MQ using JmsTemplate, with or without custom MQconnectionFactory, 
MQconnectionFactory uses the MQ connection properties such as server name , port
SSL Chiper Suite, etc.
out of the box ConnectionFactory does not support SSLContext 
with IBM MQ using TLS_RSA_WITH_AES_256_CBC_SHA256, and keystore.
jakarta.jms.MQConnectionFactory is used for injecting SSLSocketFactory
the line is commented out in the code.

2 Listener is running, 
    one from MessageConsumer, use specific Listener Container Factory "myFactory"
    another one in the main class, use auto wired JmsTemplate, which ultimately uses "myFactory"

Same as the Sender in HeartBeat and the main class
============== original writing======================

Instruction , Steps
#######################
Get an IBM MQ queue for development in a container
https://developer.ibm.com/tutorials/mq-connect-app-queue-manager-containers/ <br>
alias podman=docker <br/>
a)  docker pull icr.io/ibm-messaging/mq:latest <br>
b) docker images
                <img src="https://developer.ibm.com/developer/default/tutorials/mq-connect-app-queue-manager-containers/images/container-image-latest.png" alt="Output from the docker commands that show the latest version of MQ server"/>

c)  docker volume create qm1data
d)  docker run --env LICENSE=accept --env MQ_QMGR_NAME=QM1 --volume qm1data:/mnt/mqm --publish 1414:1414 --publish 9443:9443 --detach --env MQ_APP_USER=app --env MQ_APP_PASSWORD=passw0rd --env MQ_ADMIN_USER=admin --env MQ_ADMIN_PASSWORD=passw0rd --name QM1 icr.io/ibm-messaging/mq:latest
e)  docker ps
    <img src="https://developer.ibm.com/developer/default/tutorials/mq-connect-app-queue-manager-containers/images/running-container.png" alt="Output from running the container image"/>

f) docker exec -ti QM1 bash

    # dspmqver
        <img src="https://developer.ibm.com/developer/default/tutorials/mq-connect-app-queue-manager-containers/images/dspmqver-9.3.png" alt="Output for displaying MQ version"/>
    # dspmq
        <img src="https://developer.ibm.com/developer/default/tutorials/mq-connect-app-queue-manager-containers/images/dspmq-output.png" alt="Output for displaying the running queue managers"/>

        Inside the container, the MQ installation on RHEL has the following objects:

            Queue manager QM1
            Queue DEV.QUEUE.1
            Channel: DEV.APP.SVRCONN
            Listener: SYSTEM.LISTENER.TCP.1 on port 1414

            <img src="https://developer.ibm.com/developer/default/tutorials/mq-connect-app-queue-manager-containers/images/rsc_docker_diagram.png" alt="Objects and permissions in the Docker image for MQ"/>


Receive a message:
    curl http://127.0.1:8000/recv

Send a sample message "Hello World!"
    curl http://127.0.0.1:8000/send

Send a payment records.
curl http://127.0.0.1:8000/pay

2024-09-25T01:30:06.001Z  INFO 14260 --- [mq-spring] [ntContainer#0-1] c.example.MQ.MQService.MessageConsumer   : 11 Original Message Received  from Queue :: 
PaymentId,Amount
1234567,129.99
1234568,33.60
1234567,129.99
1234568,33.60
1234567,129.99
1234568,33.60

Heart Beat:
2024-09-25T01:31:44.750Z  INFO 14418 --- [mq-spring] [ntContainer#0-1] c.example.MQ.MQService.MessageConsumer   : 1 Original Message Received  from Queue ::
Lubb-dupp

