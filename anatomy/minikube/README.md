1 Start Minikube

minikube start
kubectl config use-context minikube

Start Minikube dashboard
    minikube dashboard

kubectl cluster-info

minikube ssh

https://www.baeldung.com/spring-boot-minikube

https://github.com/eugenp/tutorials/tree/master/spring-cloud-modules/spring-cloud-kubernetes


https://spring.io/guides/gs/spring-boot-docker
FROM openjdk:17-jdk-alpine
VOLUME /tmp
COPY target/minikube-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]

eval $(minikube -p minikube docker-env)
docker build -t minikube-hello .



imagePullPolicy: Never  // in deployment.yaml


kubectl apply -f deployment.yaml
kubectl apply -f service.yaml
    minikube service minikube-hello --url  //TODO : Issue: Failed to pull image "minikube-hello:latest": Error response from daemon: pull access denied for minikube-hello, repository does not exist or may require 'docker login': denied: requested access to the resource is denied

minikube dashboard


-- reset minikube 
	minikube delete

-- create a cron job
kubectl apply -f cronjob.yaml