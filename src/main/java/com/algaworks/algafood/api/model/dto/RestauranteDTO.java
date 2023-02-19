package com.algaworks.algafood.api.model.dto;

import com.algaworks.algafood.domain.model.Usuario;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;
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

    private Boolean aberto;

    private List<UsuarioDTO> responsaveis;



}
