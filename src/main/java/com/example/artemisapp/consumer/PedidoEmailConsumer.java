package com.example.artemisapp.consumer;

import com.example.artemisapp.dto.PedidoRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class PedidoEmailConsumer {

    private static final Logger LOGGER = LoggerFactory.getLogger(PedidoEmailConsumer.class);

    private final ObjectMapper objectMapper;

    public PedidoEmailConsumer(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @JmsListener(
            destination = "${app.jms.topics.pedido-criado}",
            containerFactory = "topicJmsListenerContainerFactory"
    )
    public void consumir(String mensagem) {
        try {
            PedidoRequest pedidoRequest = objectMapper.readValue(mensagem, PedidoRequest.class);
            // Em Topic, este consumer recebe a mesma mensagem que outros assinantes do topico.
            LOGGER.info("[EMAIL] Pedido recebido: {}", pedidoRequest.id());
        } catch (JsonProcessingException exception) {
            LOGGER.error("Nao foi possivel desserializar evento de pedido para email: {}", mensagem, exception);
        }
    }
}
