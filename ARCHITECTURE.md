# Arquitetura

## Visao Geral

A aplicacao e uma API Spring Boot que recebe pedidos por HTTP, publica cada pedido em uma fila JMS no ActiveMQ Artemis e consome mensagens da mesma fila para demonstrar o fluxo ponta a ponta.

Fluxo principal:

```text
Cliente HTTP -> PedidoController -> PedidoProducer -> ActiveMQ Artemis -> PedidoConsumer -> Logs
```

## Componentes

### PedidoController

Responsavel por expor o endpoint `POST /pedidos`.

Recebe o payload HTTP como `PedidoRequest` e delega o envio da mensagem para `PedidoProducer`.

### PedidoRequest

DTO de entrada da API.

Campos:

- `id`: identificador do pedido.
- `cliente`: nome do cliente.
- `valor`: valor do pedido.

### PedidoProducer

Responsavel por serializar o `PedidoRequest` em JSON e publicar a mensagem via `JmsTemplate`.

A destination usada pelo producer vem da propriedade:

```yaml
app:
  jms:
    queues:
      pedidos: pedidos
```

### PedidoConsumer

Responsavel por consumir mensagens da fila de pedidos usando `@JmsListener`.

A destination usada pelo consumer tambem vem da propriedade `app.jms.queues.pedidos`, garantindo que producer e consumer apontem para o mesmo nome configurado.

### ActiveMQ Artemis

Broker JMS usado pela aplicacao.

No ambiente local, e executado via Docker Compose e exposto em:

- JMS: `tcp://localhost:61616`
- Console web: `http://localhost:8161`

Credenciais locais:

- Usuario: `artemis`
- Senha: `artemis`

## Configuracao JMS

A conexao com o Artemis fica em `src/main/resources/application.yml`:

```yaml
spring:
  artemis:
    mode: native
    broker-url: tcp://localhost:61616
    user: artemis
    password: artemis
  jms:
    pub-sub-domain: false
```

`pub-sub-domain: false` indica uso de queue, nao topic.

## Fila de Pedidos

A fila usada pela aplicacao e `pedidos`.

Importante: neste projeto, a fila `pedidos` nao esta declarada explicitamente no broker Artemis.

O que acontece no ambiente atual:

- A aplicacao define o nome da fila em `app.jms.queues.pedidos`.
- Producer e consumer usam essa mesma propriedade.
- O Artemis cria ou usa a fila automaticamente quando a aplicacao publica ou consome mensagens, assumindo a configuracao padrao de auto-criacao do broker.

Essa abordagem e aceitavel para desenvolvimento local e aprendizado, mas nao e a abordagem recomendada para producao.

## Decisao Arquitetural: Auto-criacao Local

Decisao atual:

- Manter a configuracao simples para desenvolvimento local.
- Nao provisionar a fila explicitamente no `docker-compose.yml` ou em `broker.xml`.
- Documentar claramente que a fila depende de auto-criacao no broker local.

Motivo:

- O objetivo do projeto e demonstrar Spring Boot com JMS e Artemis com baixa complexidade operacional.

Consequencia:

- Um erro de digitacao no nome da fila poderia criar uma nova fila automaticamente, dependendo da configuracao do broker.
- A configuracao atual nao deve ser tratada como modelo de producao.

## Abordagem Recomendada Para Producao

Em producao, a fila deve ser provisionada no broker ou na infraestrutura, nao pela aplicacao.

Praticas recomendadas:

- Declarar a fila explicitamente no Artemis, por exemplo via `broker.xml`, Helm chart, Terraform, Ansible, operador Kubernetes ou pipeline de deploy.
- Desabilitar ou restringir auto-criacao de filas.
- Definir dead-letter queue.
- Definir politica de redelivery.
- Definir durabilidade.
- Definir limites de tamanho e comportamento quando a fila encher.
- Definir permissoes de publicacao e consumo.
- Configurar metricas e alertas.

Exemplo conceitual de declaracao no Artemis:

```xml
<address name="pedidos">
  <anycast>
    <queue name="pedidos"/>
  </anycast>
</address>
```

Para JMS queue no Artemis, `anycast` e o tipo de roteamento apropriado.
