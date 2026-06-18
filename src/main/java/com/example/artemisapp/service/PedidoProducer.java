package com.example.artemisapp.service;

import com.example.artemisapp.dto.PedidoRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

@Service
public class PedidoProducer {

    private static final String QUEUE_PEDIDOS = "pedidos";

    private final JmsTemplate jmsTemplate;
    private final ObjectMapper objectMapper;

    public PedidoProducer(JmsTemplate jmsTemplate, ObjectMapper objectMapper) {
        this.jmsTemplate = jmsTemplate;
        this.objectMapper = objectMapper;
    }

    public void enviar(PedidoRequest pedidoRequest) {
        try {
            String mensagem = objectMapper.writeValueAsString(pedidoRequest);
            jmsTemplate.convertAndSend(QUEUE_PEDIDOS, mensagem);
        } catch (JsonProcessingException exception) {
            throw new IllegalArgumentException("Nao foi possivel serializar o pedido.", exception);
        }
    }
}
