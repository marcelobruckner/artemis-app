# Contexto Para Agentes

Este arquivo deve ser lido antes de alterar o projeto. Ele contem instrucoes operacionais e aponta para a documentacao de produto e arquitetura.

## Documentos Fonte

- `PRD.md`: objetivo, escopo, requisitos funcionais, requisitos tecnicos e criterios de aceite.
- `ARCHITECTURE.md`: componentes, fluxos tecnicos, configuracao JMS, Queue vs Topic e decisoes sobre Artemis.
- `README.md`: instrucoes praticas para executar e testar o projeto localmente.

## Resumo do Projeto

API Spring Boot com Java 21 que demonstra dois modelos JMS com ActiveMQ Artemis:

- Queue Point-to-Point: `POST /pedidos` publica na queue `pedidos` e `PedidoConsumer` consome.
- Topic Publish/Subscribe: `POST /pedidos/evento` publica no topic `pedido-criado`, consumido por `PedidoEmailConsumer` e `PedidoAuditoriaConsumer`.

## Convencoes de Trabalho

- Mensagens de commit devem ser sempre em portugues.
- Manter o projeto simples e didatico, salvo pedido explicito para endurecer a configuracao.
- Nao transformar a configuracao local em uma arquitetura de producao sem solicitacao explicita.
- Preferir Maven para validacao: `mvn test`.
- Se alterar comportamento, atualizar `PRD.md` quando mudar requisito ou escopo.
- Se alterar desenho tecnico, fluxo, configuracao JMS ou decisoes sobre Artemis, atualizar `ARCHITECTURE.md`.
- Se alterar instrucoes de execucao local, atualizar `README.md`.

## Decisoes Importantes Ja Tomadas

- A queue `pedidos` nao e provisionada explicitamente no broker neste projeto.
- O topic `pedido-criado` tambem depende da configuracao local do Artemis para desenvolvimento.
- O projeto usa auto-criacao do Artemis para desenvolvimento local. Essa decisao esta documentada em `ARCHITECTURE.md` e nao deve ser alterada sem pedido explicito do usuario.
- A funcionalidade de Queue deve continuar independente da funcionalidade de Topic.
- Seguir o fluxo `Controller -> Service -> Producer/Consumer`.
- Manter todos os producers no pacote `producer` e todos os consumers no pacote `consumer`.
- Nao alterar o comportamento de `POST /pedidos`, `PedidoProducer` ou `PedidoConsumer` ao evoluir o fluxo Publish/Subscribe.
