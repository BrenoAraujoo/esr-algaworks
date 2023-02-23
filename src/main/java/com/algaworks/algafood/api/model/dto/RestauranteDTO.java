package com.algaworks.algafood.api.model.dto;

import com.algaworks.algafood.api.model.views.RestauranteView;
import com.algaworks.algafood.domain.model.Usuario;
import com.fasterxml.jackson.annotation.JsonView;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RestauranteDTO {

    @JsonView({RestauranteView.Resumo.class, RestauranteView.IdNome.class})
    private Long id;

    @JsonView({RestauranteView.Resumo.class, RestauranteView.IdNome.class})
    private String nome;
    private BigDecimal taxaFrete;

    private  Boolean ativo;
    @JsonView(RestauranteView.Resumo.class)
    private CozinhaDTO cozinha;

    private EnderecoDTO endereco;

    private Boolean aberto;

    private List<UsuarioDTO> responsaveis;



}
