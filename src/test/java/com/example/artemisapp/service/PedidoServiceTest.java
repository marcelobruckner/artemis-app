package com.example.artemisapp.service;

import com.example.artemisapp.dto.PedidoRequest;
import com.example.artemisapp.producer.PedidoProducer;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class PedidoServiceTest {

    private final PedidoProducer pedidoProducer = mock(PedidoProducer.class);
    private final PedidoService pedidoService = new PedidoService(pedidoProducer);

    @Test
    void deveDelegarCriacaoDePedidoParaProducerDaQueue() {
        PedidoRequest pedidoRequest = new PedidoRequest(1L, "Luis", new BigDecimal("100.00"));

        pedidoService.criarPedido(pedidoRequest);

        verify(pedidoProducer).enviar(pedidoRequest);
    }
}
