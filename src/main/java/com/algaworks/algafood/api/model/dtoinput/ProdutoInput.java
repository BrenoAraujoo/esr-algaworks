package com.algaworks.algafood.api.model.dtoinput;

import java.math.BigDecimal;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProdutoInput {

    @NotBlank
    private String nome;

    @NotNull
    private String descricao;
    @PositiveOrZero
    private BigDecimal preco;
    @NotNull
    private Boolean ativo;
    @NotNull
    @Valid
    private RestauranteIdInput restaurante;
}
