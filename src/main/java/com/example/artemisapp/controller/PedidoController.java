package com.example.artemisapp.controller;

import com.example.artemisapp.dto.PedidoRequest;
import com.example.artemisapp.service.PedidoEventoService;
import com.example.artemisapp.service.PedidoService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {

    private final PedidoService pedidoService;
    private final PedidoEventoService pedidoEventoService;

    public PedidoController(PedidoService pedidoService, PedidoEventoService pedidoEventoService) {
        this.pedidoService = pedidoService;
        this.pedidoEventoService = pedidoEventoService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void criarPedido(@RequestBody PedidoRequest pedidoRequest) {
        pedidoService.criarPedido(pedidoRequest);
    }

    @PostMapping("/evento")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void publicarEventoPedidoCriado(@RequestBody PedidoRequest pedidoRequest) {
        pedidoEventoService.publicarPedidoCriado(pedidoRequest);
    }
}
