package com.algaworks.algafood.api.assembler.usuario;

import com.algaworks.algafood.api.model.dtoinput.UsuarioAtualizarInput;
import com.algaworks.algafood.api.model.dtoinput.UsuarioInput;
import com.algaworks.algafood.domain.model.Usuario;
import javax.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UsuarioDisassembler {

    @Autowired
    private ModelMapper mapper;

    public Usuario toDomainObject(UsuarioInput usuarioInput){
        return mapper.map(usuarioInput, Usuario.class);
    }

    public void copyFromInputToDomain(@Valid UsuarioAtualizarInput usuarioInput, Usuario usuario){
        mapper.map(usuarioInput,usuario);
    }

}
