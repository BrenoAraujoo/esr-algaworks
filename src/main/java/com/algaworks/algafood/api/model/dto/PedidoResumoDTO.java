package com.algaworks.algafood.api.model.dto;


import com.algaworks.algafood.domain.model.StatusPedido;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PedidoResumoDTO {

    private Long id;
    private BigDecimal taxaFrete;
    private BigDecimal valorTotal;
    private StatusPedido status;
    private OffsetDateTime dataCriacao;
    private UsuarioDTO cliente;
    private RestauranteResumoDTO restaurante;

}
