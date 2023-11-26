# Microservice Observability Demo
Demo setup for Spring Boot 3 with Prometheus, Grafana, Loki, and Tempo to demonstrate Observability use-cases.

## Start dependencies

```shell
docker compose -p observability up 
```

## Stop dependencies

```shell
docker compose -p observability down
```

## Stop dependencies and purge data

```shell
docker compose -p observability down --volumes 
```

## Start the app

```shell
./mvnw spring-boot:run
```

## Local Infrastrucure Services

- Prometheus: http://localhost:9090
- Loki, Grafana, Tempo: http://localhost:3000
- ToxiProxy UI (failure injection): http://localhost:8484
- MailDev (emails for alerts): http://localhost:3001
- Adminer (DB Admin UI): http://localhost:8888 (credentials: `root:password`)
