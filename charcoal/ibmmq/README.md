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


