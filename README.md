# Microservice Observability Demo    [![LinkedIn](https://img.shields.io/badge/LinkedIn-0077B5?style=for-the-badge&logo=linkedin&logoColor=white)](https://linkedin.com/in/wandi)

Demo setup for Spring Boot 3 with Prometheus, Grafana, Loki, and Tempo to demonstrate Observability use-cases.

> [!NOTE]  
> You must have Java 17 or latter and Maven 3 to run this project

```shell
brew install java
```
```shell
brew install maven
```

## Start infrastructure dependencies

```shell
docker compose -p observability up 
```

## Stop infrastructure dependencies

```shell
docker compose -p observability down
```

## Stop infrastructure dependencies and purge data

```shell
docker compose -p observability down --volumes 
```

## Start the demos

> [!IMPORTANT]  
> Before run the demos, you need to add kafka host to your /etc/hosts
```shell
sudo -- sh -c -e "echo '127.0.0.1   kafka' >> /etc/hosts";
```

Run forrest, run.

```shell
./mvnw spring-boot:run -pl kafka-producer-demo
```

```shell
./mvnw spring-boot:run -pl kafka-consumer-demo
```

> [!NOTE]  
> Take a look on [Makefile](https://github.com/building-resilient-microservices/observability/blob/main/Makefile), there are scripts to simulate latency, errors etc.

## Local Infrastrucure Services

- Prometheus: http://localhost:9090
- Loki, Grafana, Tempo: http://localhost:3000
- ToxiProxy UI (failure injection): http://localhost:8484
- MailDev (emails for alerts): http://localhost:3001
- Adminer (DB Admin UI): http://localhost:8888 (credentials: `root:password`)
- Kafka Drop: http://localhost:9000

## TIPS
You can filter specific metrics in URI

```shell
cURL -X GET 'http://localhost:8081/actuator/metrics/method.observed?tag=class:com.example.messaging.service.FactService&tag=method:read' | jq .
```
