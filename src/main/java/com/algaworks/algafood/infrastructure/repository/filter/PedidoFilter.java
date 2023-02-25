package com.algaworks.algafood.infrastructure.repository.filter;

import java.time.OffsetDateTime;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

@Setter
@Getter
public class PedidoFilter {

    private Long clienteId;
    private Long restauranteId;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private OffsetDateTime dataCriacaoInicio;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private OffsetDateTime dataCriacaoFim;
}
