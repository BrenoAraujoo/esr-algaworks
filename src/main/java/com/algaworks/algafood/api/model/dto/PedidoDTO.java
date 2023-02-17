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
    private EnderecoDTO enderecoEntrega;
    private StatusPedido status;
    private OffsetDateTime dataCriacao;
    private FormaPagamentoDTO formaPagamento;
    private OffsetDateTime dataConfirmacao;
    private OffsetDateTime dataCancelamento;
    private OffsetDateTime dataEntrega;
    private UsuarioDTO cliente;
    private RestauranteResumoDTO restaurante;
    private List<ItemPedidoDTO> itens = new ArrayList<>();
}
