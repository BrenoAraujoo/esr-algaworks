package com.algaworks.algafood.api.model.dtoinput;

import com.algaworks.algafood.api.model.dto.UsuarioDTO;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioSenhaDTO extends UsuarioDTO {

    private String senhaAtual;
    private String novaSenha;
}
