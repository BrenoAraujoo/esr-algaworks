package com.algaworks.algafood.api.model.dto;

import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GrupoDTO {

    private Long id;

    private String nome;
    private List<PermissaoDTO> permissoes = new ArrayList<>();

}
