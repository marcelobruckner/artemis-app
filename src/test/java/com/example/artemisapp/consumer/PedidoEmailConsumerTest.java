package com.example.artemisapp.consumer;

import com.example.artemisapp.dto.PedidoRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class PedidoEmailConsumerTest {

    private final ObjectMapper objectMapper = mock(ObjectMapper.class);
    private final PedidoEmailConsumer pedidoEmailConsumer = new PedidoEmailConsumer(objectMapper);

    @Test
    void deveDesserializarMensagemRecebidaParaSimularEmail() throws Exception {
        String mensagem = "{\"id\":1,\"cliente\":\"Luis\",\"valor\":100.00}";
        PedidoRequest pedidoRequest = new PedidoRequest(1L, "Luis", new BigDecimal("100.00"));
        when(objectMapper.readValue(mensagem, PedidoRequest.class)).thenReturn(pedidoRequest);

        pedidoEmailConsumer.consumir(mensagem);

        verify(objectMapper).readValue(mensagem, PedidoRequest.class);
    }
}
