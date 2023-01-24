package com.algaworks.algafood.api.model.dtoinput;

import com.algaworks.algafood.api.model.dto.CidadeResumoDTO;
import com.algaworks.algafood.domain.model.Cidade;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EnderecoInput {
    @NotNull
    private String cep;
    @NotNull
    private String logradouro;
    @NotNull
    private String numero;
    private String complemento;
    @NotNull
    private String bairro;
    @NotNull
    @Valid
    private CidadeIdInput cidade;

}
