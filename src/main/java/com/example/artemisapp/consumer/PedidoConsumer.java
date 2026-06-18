package com.example.artemisapp.consumer;

import com.example.artemisapp.dto.PedidoRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class PedidoConsumer {

    private static final Logger LOGGER = LoggerFactory.getLogger(PedidoConsumer.class);

    private final ObjectMapper objectMapper;

    public PedidoConsumer(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @JmsListener(destination = "${app.jms.queues.pedidos}")
    public void consumir(String mensagem) {
        try {
            PedidoRequest pedidoRequest = objectMapper.readValue(mensagem, PedidoRequest.class);
            LOGGER.info("Pedido consumido: id={}, cliente={}, valor={}",
                    pedidoRequest.id(),
                    pedidoRequest.cliente(),
                    pedidoRequest.valor());
        } catch (JsonProcessingException exception) {
            LOGGER.error("Nao foi possivel desserializar a mensagem da fila pedidos: {}", mensagem, exception);
        }
    }
}
