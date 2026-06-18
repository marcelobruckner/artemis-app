package com.example.artemisapp.controller;

import com.example.artemisapp.dto.PedidoRequest;
import com.example.artemisapp.service.PedidoEventoService;
import com.example.artemisapp.service.PedidoProducer;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;

class PedidoControllerTest {

    private final PedidoProducer pedidoProducer = mock(PedidoProducer.class);
    private final PedidoEventoService pedidoEventoService = mock(PedidoEventoService.class);
    private final PedidoController pedidoController = new PedidoController(pedidoProducer, pedidoEventoService);

    @Test
    void deveManterCriacaoDePedidoViaQueue() {
        PedidoRequest pedidoRequest = new PedidoRequest(1L, "Luis", new BigDecimal("100.00"));

        pedidoController.criarPedido(pedidoRequest);

        verify(pedidoProducer).enviar(pedidoRequest);
        verifyNoInteractions(pedidoEventoService);
    }

    @Test
    void devePublicarEventoDePedidoCriadoViaTopic() {
        PedidoRequest pedidoRequest = new PedidoRequest(1L, "Luis", new BigDecimal("100.00"));

        pedidoController.publicarEventoPedidoCriado(pedidoRequest);

        verify(pedidoEventoService).publicarPedidoCriado(pedidoRequest);
        verifyNoInteractions(pedidoProducer);
    }
}
