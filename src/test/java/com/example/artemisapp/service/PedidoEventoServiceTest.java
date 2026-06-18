package com.example.artemisapp.service;

import com.example.artemisapp.dto.PedidoRequest;
import com.example.artemisapp.producer.PedidoEventoProducer;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class PedidoEventoServiceTest {

    private final PedidoEventoProducer pedidoEventoProducer = mock(PedidoEventoProducer.class);
    private final PedidoEventoService pedidoEventoService = new PedidoEventoService(pedidoEventoProducer);

    @Test
    void deveDelegarPublicacaoParaProducerDeEvento() {
        PedidoRequest pedidoRequest = new PedidoRequest(1L, "Luis", new BigDecimal("100.00"));

        pedidoEventoService.publicarPedidoCriado(pedidoRequest);

        verify(pedidoEventoProducer).publicar(pedidoRequest);
    }
}
