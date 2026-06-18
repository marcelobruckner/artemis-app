# Contexto do Projeto

Este projeto e uma API Spring Boot com Java 21 que publica e consome mensagens JMS em um broker ActiveMQ Artemis local.

## Stack

- Java 21
- Spring Boot
- Spring Web
- Spring JMS com ActiveMQ Artemis
- Maven
- Docker Compose

## Comportamento da Aplicacao

- O endpoint `POST /pedidos` recebe um `PedidoRequest` com `id`, `cliente` e `valor`.
- `PedidoProducer` serializa o pedido em JSON e envia para a fila configurada em `app.jms.queues.pedidos`.
- `PedidoConsumer` consome mensagens da mesma fila usando `@JmsListener`.
- O broker Artemis local fica em `tcp://localhost:61616`.

## Decisao Sobre a Fila

A fila `pedidos` nao esta declarada explicitamente no broker Artemis neste projeto.

Para desenvolvimento local, o projeto depende da auto-criacao do Artemis quando a aplicacao publica ou consome a destination `pedidos`.

Em producao, a abordagem correta seria provisionar a fila no broker ou na infraestrutura e evitar depender de auto-criacao.

## Convencoes

- Mensagens de commit devem ser sempre em portugues.
- Manter o projeto simples e didatico, sem adicionar configuracoes de producao se o usuario nao pedir explicitamente.
- Preferir explicar a diferenca entre configuracao da aplicacao e configuracao do broker quando o assunto for JMS/Artemis.
