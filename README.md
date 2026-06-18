# Artemis App

API de pedidos criada com Spring Boot, Java 21, Spring Web e Spring JMS para publicar e consumir mensagens em uma fila ActiveMQ Artemis.

## Requisitos

- Java 21
- Maven 3.9+
- Docker e Docker Compose

## Subir o ActiveMQ Artemis

```bash
docker compose up -d
```

O broker ficará disponível em:

- JMS: `tcp://localhost:61616`
- Console web: `http://localhost:8161`
- Usuario: `artemis`
- Senha: `artemis`

## Executar a aplicação

```bash
mvn spring-boot:run
```

A aplicação sobe em `http://localhost:8080`.

## Publicar um pedido

```bash
curl -X POST http://localhost:8080/pedidos \
  -H "Content-Type: application/json" \
  -d '{
    "id": 1,
    "cliente": "Maria Silva",
    "valor": 199.90
  }'
```

O endpoint retorna `202 Accepted`. A mensagem é publicada na fila `pedidos` e consumida pelo `PedidoConsumer`, que registra o pedido nos logs da aplicação.

## Arquitetura

- `POST /pedidos`: recebe um `PedidoRequest`.
- `PedidoProducer`: serializa o pedido em JSON e envia para a queue configurada em `app.jms.queues.pedidos`.
- `PedidoConsumer`: consome mensagens da queue configurada em `app.jms.queues.pedidos` com `@JmsListener`.
- `application.yml`: configura conexão nativa com ActiveMQ Artemis em `localhost:61616` e define a fila `pedidos`.
