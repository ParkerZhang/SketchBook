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


-- Helm download from https://github.com/helm/helm/releases ,
    tar -zxvf helm-v3.0.0-linux-amd64.tar.gz
    sudo install helm /usr/local/bin

helm repo add bitnami https://charts.bitnami.com/bitnami
helm search repo bitnami
helm repo update              # Make sure we get the latest list of charts
helm install bitnami/mysql --generate-name
helm uninstall mysql-1729119436
helm status mysql-1729119436



# helm generates templates for kubectl 
helm template my-release my-chart > my-manifest.yaml
kubectl apply -f my-manifest.yaml

//TODO : 
    1. helm install ... .. deployment.yaml (template ) pull docker image failed
imagePullPolicy: Never  # for local images
    2. helm install .. specific deployment.yaml , minikube-hello , --url/hello not responding

helm install my-hello-world ./my-hello-chart/
eval $(minikube -p minikube docker-env)
docker build -t minikube-hello .
minikube service my-hello-service --url