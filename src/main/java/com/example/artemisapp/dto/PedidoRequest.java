package com.example.artemisapp.dto;

import java.math.BigDecimal;

public record PedidoRequest(
        Long id,
        String cliente,
        BigDecimal valor
) {
}
