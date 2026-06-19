# Artemis App

API de pedidos criada com Spring Boot, Java 21, Spring Web e Spring JMS para publicar e consumir mensagens em uma fila ActiveMQ Artemis.

## Documentacao

- `PRD.md`: objetivo, escopo, requisitos e criterios de aceite.
- `ARCHITECTURE.md`: arquitetura, fluxo JMS, configuracao da fila e decisoes tecnicas.
- `AGENTS.md`: contexto operacional para agentes e convencoes do projeto.
- `TODO.md`: backlog tecnico com evolucoes planejadas.

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

## Queue vs Topic

### Queue: Point-to-Point

Uma Queue representa comunicacao ponto a ponto. Quando uma mensagem e enviada para uma queue, apenas um consumidor deve processar aquela mensagem.

Neste projeto, `POST /pedidos` usa a queue `pedidos`:

```text
POST /pedidos -> Queue pedidos -> PedidoConsumer
```

Esse modelo e indicado para comandos ou tarefas que devem ser executadas uma unica vez.

### Topic: Publish/Subscribe

Um Topic representa comunicacao por publicacao e assinatura. Quando uma mensagem e publicada em um topic, varios consumidores independentes podem receber a mesma mensagem.

Neste projeto, `POST /pedidos/evento` usa o topic `pedido-criado`:

```text
POST /pedidos/evento -> Topic pedido-criado -> PedidoEmailConsumer
                                           -> PedidoAuditoriaConsumer
```

Esse modelo e indicado para eventos que precisam ser observados por mais de uma parte do sistema.

## Publicar Evento de Pedido Criado

```bash
curl -X POST http://localhost:8080/pedidos/evento \
  -H "Content-Type: application/json" \
  -d '{
    "id": 1,
    "cliente": "Luis",
    "valor": 100.00
  }'
```

O endpoint retorna `202 Accepted`. A mensagem e publicada no topic `pedido-criado` e deve ser recebida pelos dois consumers:

```text
[EMAIL] Pedido recebido: 1
[AUDITORIA] Pedido recebido: 1
```
