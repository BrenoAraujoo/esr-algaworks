package com.algaworks.algafood.api.model.dto;


import com.algaworks.algafood.domain.model.*;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PedidoDTO {

    private Long id;
    private BigDecimal taxaFrete;
    private BigDecimal valorTotal;
    private BigDecimal subtotal;
    private StatusPedido status;
    private OffsetDateTime dataCriacao;
    private OffsetDateTime dataConfirmacao;
    private OffsetDateTime dataEntrega;
    private OffsetDateTime dataCancelamento;

    private EnderecoDTO enderecoEntrega;
    private FormaPagamentoDTO formaPagamento;

    private RestauranteResumoDTO restaurante;
    private UsuarioDTO cliente;
    private List<ItemPedidoDTO> itens = new ArrayList<>();
}
