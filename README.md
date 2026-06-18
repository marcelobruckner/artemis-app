# Artemis App

API de pedidos criada com Spring Boot, Java 21, Spring Web e Spring JMS para publicar e consumir mensagens em uma fila ActiveMQ Artemis.

## Documentacao

- `PRD.md`: objetivo, escopo, requisitos e criterios de aceite.
- `ARCHITECTURE.md`: arquitetura, fluxo JMS, configuracao da fila e decisoes tecnicas.
- `AGENTS.md`: contexto operacional para agentes e convencoes do projeto.

## Requisitos

- Java 21
- Maven 3.9+
- Docker e Docker Compose

## Subir o ActiveMQ Artemis

```bash
docker compose up -d
```

O broker ficara disponivel em:

- JMS: `tcp://localhost:61616`
- Console web: `http://localhost:8161`
- Usuario: `artemis`
- Senha: `artemis`

## Executar a Aplicacao

```bash
mvn spring-boot:run
```

A aplicacao sobe em `http://localhost:8080`.

## Publicar um Pedido

```bash
curl -X POST http://localhost:8080/pedidos \
  -H "Content-Type: application/json" \
  -d '{
    "id": 1,
    "cliente": "Maria Silva",
    "valor": 199.90
  }'
```

O endpoint retorna `202 Accepted`. A mensagem e publicada na fila `pedidos` e consumida pelo `PedidoConsumer`, que registra o pedido nos logs da aplicacao.

## Fluxo Resumido

```text
Cliente HTTP -> PedidoController -> PedidoProducer -> ActiveMQ Artemis -> PedidoConsumer -> Logs
```

## Observacao Sobre a Fila

Neste projeto, a fila `pedidos` nao esta declarada explicitamente no broker Artemis. Para desenvolvimento local, o projeto depende da auto-criacao do broker.

A explicacao completa e as implicacoes para producao estao em `ARCHITECTURE.md`.
