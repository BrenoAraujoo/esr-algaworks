package com.algaworks.algafood.api.model.dtoinput;


import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioAtualizarInput {

    @NotBlank
    private String nome;
    @NotBlank
    @Email
    private String email;


}
