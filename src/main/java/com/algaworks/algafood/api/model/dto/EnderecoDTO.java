package com.algaworks.algafood.api.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EnderecoDTO {
    private String cep;
    private String logradouro;
    private String numero;
    private String complemento;
    private String bairro;

    private  CidadeResumoDTO cidade;
}
