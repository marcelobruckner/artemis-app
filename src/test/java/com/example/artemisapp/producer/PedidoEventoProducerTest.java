package com.example.artemisapp.producer;

import com.example.artemisapp.dto.PedidoRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.jms.core.JmsTemplate;

import java.math.BigDecimal;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class PedidoEventoProducerTest {

    private final JmsTemplate topicJmsTemplate = mock(JmsTemplate.class);
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final PedidoEventoProducer pedidoEventoProducer = new PedidoEventoProducer(
            topicJmsTemplate,
            objectMapper,
            "pedido-criado"
    );

    @Test
    void devePublicarPedidoSerializadoNoTopicConfigurado() {
        PedidoRequest pedidoRequest = new PedidoRequest(1L, "Luis", new BigDecimal("100.00"));

        pedidoEventoProducer.publicar(pedidoRequest);

        verify(topicJmsTemplate).convertAndSend(
                "pedido-criado",
                "{\"id\":1,\"cliente\":\"Luis\",\"valor\":100.00}"
        );
    }
}
