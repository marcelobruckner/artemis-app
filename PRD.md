# PRD - API de Pedidos com Artemis

## Objetivo

Criar uma API de pedidos que receba requisicoes HTTP e publique mensagens em uma fila ActiveMQ Artemis usando Spring JMS.

## Publico-alvo

- Desenvolvedores que estao estudando integracao entre Spring Boot e mensageria JMS.
- Pessoas que precisam de um exemplo local e simples de producer e consumer com ActiveMQ Artemis.

## Escopo

O projeto deve oferecer uma API REST para criar pedidos e demonstrar o envio e consumo de mensagens em uma fila JMS.

Incluido no escopo:

- Endpoint HTTP para receber pedidos.
- DTO de entrada para representar o pedido.
- Producer JMS para publicar mensagens.
- Consumer JMS para consumir mensagens.
- Configuracao local do broker Artemis via Docker Compose.
- Documentacao de execucao local.

Fora do escopo neste momento:

- Persistencia em banco de dados.
- Autenticacao e autorizacao.
- Validacoes avancadas de payload.
- Observabilidade completa.
- Provisionamento explicito da fila no broker.
- Configuracao pronta para producao.

## Requisitos Funcionais

- A API deve expor o endpoint `POST /pedidos`.
- O endpoint deve receber um JSON com `id`, `cliente` e `valor`.
- A aplicacao deve serializar o pedido recebido em JSON.
- A aplicacao deve publicar a mensagem na fila configurada para pedidos.
- A aplicacao deve consumir mensagens da mesma fila.
- O consumer deve registrar em log os dados do pedido consumido.

## Requisitos Tecnicos

- Usar Java 21.
- Usar Spring Boot.
- Usar Spring Web para a API HTTP.
- Usar Spring JMS com ActiveMQ Artemis.
- Usar Maven como ferramenta de build.
- Usar Docker Compose para subir o Artemis localmente.
- Configurar o broker local em `localhost:61616`.

## Contrato da API

### Criar Pedido

`POST /pedidos`

Payload:

```json
{
  "id": 1,
  "cliente": "Maria Silva",
  "valor": 199.90
}
```

Resposta esperada:

```text
202 Accepted
```

## Criterios de Aceite

- O projeto compila com `mvn test`.
- O broker Artemis sobe com `docker compose up -d`.
- A aplicacao sobe com `mvn spring-boot:run`.
- Uma chamada para `POST /pedidos` publica uma mensagem na fila de pedidos.
- O consumer consome a mensagem e registra o pedido nos logs.
