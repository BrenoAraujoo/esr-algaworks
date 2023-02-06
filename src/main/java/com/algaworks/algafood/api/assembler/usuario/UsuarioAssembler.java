package com.algaworks.algafood.api.assembler.usuario;

import com.algaworks.algafood.api.model.dto.UsuarioDTO;
import com.algaworks.algafood.domain.model.Usuario;
import java.util.List;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UsuarioAssembler {

    @Autowired
    private ModelMapper mapper;

    public UsuarioDTO toDTO (Usuario usuario){
        return mapper.map(usuario,UsuarioDTO.class );
    }

    public List<UsuarioDTO> listToDTO(List<Usuario> usuarioList){
        return usuarioList.stream()
                .map(this::toDTO)
                .toList();
    }

}
