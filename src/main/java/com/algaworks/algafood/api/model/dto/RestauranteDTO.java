package com.algaworks.algafood.api.model.dto;

import java.math.BigDecimal;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RestauranteDTO {
    private Long idRestauranteDTO;

    private String nomeDoRestauranteDTO;
    private BigDecimal taxaFrete;
    private CozinhaDTO cozinha;

}
