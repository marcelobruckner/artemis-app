# Contexto Para Agentes

Este arquivo deve ser lido antes de alterar o projeto. Ele contem instrucoes operacionais e aponta para a documentacao de produto e arquitetura.

## Documentos Fonte

- `PRD.md`: objetivo, escopo, requisitos funcionais, requisitos tecnicos e criterios de aceite.
- `ARCHITECTURE.md`: componentes, fluxo tecnico, configuracao JMS, decisao sobre a fila Artemis e recomendacoes de producao.
- `README.md`: instrucoes praticas para executar o projeto localmente.

## Resumo do Projeto

API Spring Boot com Java 21 que recebe pedidos via `POST /pedidos`, publica mensagens JMS em ActiveMQ Artemis e consome mensagens da mesma fila para demonstrar o fluxo ponta a ponta.

## Convencoes de Trabalho

- Mensagens de commit devem ser sempre em portugues.
- Manter o projeto simples e didatico, salvo pedido explicito para endurecer a configuracao.
- Nao transformar a configuracao local em uma arquitetura de producao sem solicitacao explicita.
- Preferir Maven para validacao: `mvn test`.
- Se alterar comportamento, atualizar `PRD.md` quando mudar requisito ou escopo.
- Se alterar desenho tecnico, fluxo, configuracao JMS ou decisoes sobre Artemis, atualizar `ARCHITECTURE.md`.
- Se alterar instrucoes de execucao local, atualizar `README.md`.

## Decisao Importante Ja Tomada

A fila `pedidos` nao e provisionada explicitamente no broker neste projeto.

O projeto usa auto-criacao do Artemis para desenvolvimento local. Essa decisao esta documentada em `ARCHITECTURE.md` e nao deve ser alterada sem pedido explicito do usuario.
