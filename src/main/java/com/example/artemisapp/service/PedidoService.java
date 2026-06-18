package com.example.artemisapp.service;

import com.example.artemisapp.dto.PedidoRequest;
import com.example.artemisapp.producer.PedidoProducer;
import org.springframework.stereotype.Service;

@Service
public class PedidoService {

    private final PedidoProducer pedidoProducer;

    public PedidoService(PedidoProducer pedidoProducer) {
        this.pedidoProducer = pedidoProducer;
    }

    public void criarPedido(PedidoRequest pedidoRequest) {
        pedidoProducer.enviar(pedidoRequest);
    }
}
