package com.algaworks.algafood.api.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CidadeDTO {

//    @JsonView(Views.Public.class)
    private Long id;
//    @JsonView(Views.Public.class)
    private String nome;
//    @JsonView(Views.Internal.class)
    private EstadoDTO estado;
}
