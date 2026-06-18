package com.example.artemisapp.service;

import com.example.artemisapp.dto.PedidoRequest;
import com.example.artemisapp.producer.PedidoEventoProducer;
import org.springframework.stereotype.Service;

@Service
public class PedidoEventoService {

    private final PedidoEventoProducer pedidoEventoProducer;

    public PedidoEventoService(PedidoEventoProducer pedidoEventoProducer) {
        this.pedidoEventoProducer = pedidoEventoProducer;
    }

    public void publicarPedidoCriado(PedidoRequest pedidoRequest) {
        pedidoEventoProducer.publicar(pedidoRequest);
    }
}
