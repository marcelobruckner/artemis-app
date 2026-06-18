package com.example.artemisapp.controller;

import com.example.artemisapp.dto.PedidoRequest;
import com.example.artemisapp.service.PedidoEventoService;
import com.example.artemisapp.service.PedidoService;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;

class PedidoControllerTest {

    private final PedidoService pedidoService = mock(PedidoService.class);
    private final PedidoEventoService pedidoEventoService = mock(PedidoEventoService.class);
    private final PedidoController pedidoController = new PedidoController(pedidoService, pedidoEventoService);

    @Test
    void deveManterCriacaoDePedidoViaQueue() {
        PedidoRequest pedidoRequest = new PedidoRequest(1L, "Luis", new BigDecimal("100.00"));

        pedidoController.criarPedido(pedidoRequest);

        verify(pedidoService).criarPedido(pedidoRequest);
        verifyNoInteractions(pedidoEventoService);
    }

    @Test
    void devePublicarEventoDePedidoCriadoViaTopic() {
        PedidoRequest pedidoRequest = new PedidoRequest(1L, "Luis", new BigDecimal("100.00"));

        pedidoController.publicarEventoPedidoCriado(pedidoRequest);

        verify(pedidoEventoService).publicarPedidoCriado(pedidoRequest);
        verifyNoInteractions(pedidoService);
    }
}
