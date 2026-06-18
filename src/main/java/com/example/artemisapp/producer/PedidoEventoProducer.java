package com.example.artemisapp.producer;

import com.example.artemisapp.dto.PedidoRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

@Component
public class PedidoEventoProducer {

    private final JmsTemplate topicJmsTemplate;
    private final ObjectMapper objectMapper;
    private final String topicPedidoCriado;

    public PedidoEventoProducer(
            @Qualifier("topicJmsTemplate") JmsTemplate topicJmsTemplate,
            ObjectMapper objectMapper,
            @Value("${app.jms.topics.pedido-criado}") String topicPedidoCriado
    ) {
        this.topicJmsTemplate = topicJmsTemplate;
        this.objectMapper = objectMapper;
        this.topicPedidoCriado = topicPedidoCriado;
    }

    public void publicar(PedidoRequest pedidoRequest) {
        try {
            String mensagem = objectMapper.writeValueAsString(pedidoRequest);
            // Topic implementa Publish/Subscribe: cada consumidor inscrito recebe uma copia da mensagem.
            topicJmsTemplate.convertAndSend(topicPedidoCriado, mensagem);
        } catch (JsonProcessingException exception) {
            throw new IllegalArgumentException("Nao foi possivel serializar o evento de pedido criado.", exception);
        }
    }
}
