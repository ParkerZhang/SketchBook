This app is to study the spring shutdown mechanism and how it works in kubernetes.

1. Helm to deploy the app to minikube

mkdir -p spring-shutdown-chart
update values.yaml
    image:
        repository: spring-shutdown
        # This sets the pull policy for images.
        pullPolicy: IfNotPresent
        # Overrides the image tag whose default is the chart appVersion.
        tag: latest


minikube start
eval $(minikube -p minikube docker-env)

docker build -t spring-shutdown:latest .

cd SringShutdown
    