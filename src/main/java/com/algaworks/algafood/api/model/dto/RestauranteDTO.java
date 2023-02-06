package com.algaworks.algafood.api.model.dto;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RestauranteDTO {
    private Long id;

    private String nome;
    private BigDecimal taxaFrete;

    private  Boolean ativo;
    private CozinhaDTO cozinha;

    private EnderecoDTO endereco;


}
