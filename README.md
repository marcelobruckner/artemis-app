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

## Configuracao da fila no Artemis

Neste projeto, a fila `pedidos` nao esta declarada explicitamente no broker Artemis.

O que existe hoje:

- A aplicacao define o nome da fila em `application.yml`, na propriedade `app.jms.queues.pedidos`.
- O producer e o consumer usam essa mesma propriedade para publicar e consumir mensagens.
- O Artemis cria ou usa a fila `pedidos` automaticamente quando a aplicacao interage com ela, assumindo a configuracao padrao de auto-criacao do broker.

Isso significa que a configuracao atual e suficiente para desenvolvimento local, mas nao representa uma configuracao robusta de producao.

Em ambientes de producao, a abordagem recomendada e:

- Provisionar a fila no broker ou na infraestrutura, por exemplo via `broker.xml`, Helm chart, Terraform, Ansible, operador Kubernetes ou pipeline de deploy.
- Desabilitar ou restringir auto-criacao de filas para evitar filas criadas por erro de digitacao.
- Definir politicas operacionais da fila, como durabilidade, dead-letter queue, redelivery, limites, seguranca, metricas e alertas.
- Manter a aplicacao apenas como produtora/consumidora da destination ja existente.

Exemplo conceitual de declaracao no Artemis:

```xml
<address name="pedidos">
  <anycast>
    <queue name="pedidos"/>
  </anycast>
</address>
```

Para este projeto, a decisao foi manter a configuracao simples e local, sem provisionar a fila explicitamente no broker.
