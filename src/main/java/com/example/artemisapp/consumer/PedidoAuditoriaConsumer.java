package com.example.artemisapp.consumer;

import com.example.artemisapp.dto.PedidoRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class PedidoAuditoriaConsumer {

    private static final Logger LOGGER = LoggerFactory.getLogger(PedidoAuditoriaConsumer.class);

    private final ObjectMapper objectMapper;

    public PedidoAuditoriaConsumer(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @JmsListener(
            destination = "${app.jms.topics.pedido-criado}",
            containerFactory = "topicJmsListenerContainerFactory"
    )
    public void consumir(String mensagem) {
        try {
            PedidoRequest pedidoRequest = objectMapper.readValue(mensagem, PedidoRequest.class);
            // Em Topic, auditoria e email recebem copias independentes do mesmo evento publicado.
            LOGGER.info("[AUDITORIA] Pedido recebido: {}", pedidoRequest.id());
        } catch (JsonProcessingException exception) {
            LOGGER.error("Nao foi possivel desserializar evento de pedido para auditoria: {}", mensagem, exception);
        }
    }
}
