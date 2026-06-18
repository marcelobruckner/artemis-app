package com.example.artemisapp.producer;

import com.example.artemisapp.dto.PedidoRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

@Service
public class PedidoProducer {

    private final JmsTemplate jmsTemplate;
    private final ObjectMapper objectMapper;
    private final String queuePedidos;

    public PedidoProducer(
            JmsTemplate jmsTemplate,
            ObjectMapper objectMapper,
            @Value("${app.jms.queues.pedidos}") String queuePedidos
    ) {
        this.jmsTemplate = jmsTemplate;
        this.objectMapper = objectMapper;
        this.queuePedidos = queuePedidos;
    }

    public void enviar(PedidoRequest pedidoRequest) {
        try {
            String mensagem = objectMapper.writeValueAsString(pedidoRequest);
            jmsTemplate.convertAndSend(queuePedidos, mensagem);
        } catch (JsonProcessingException exception) {
            throw new IllegalArgumentException("Nao foi possivel serializar o pedido.", exception);
        }
    }
}
