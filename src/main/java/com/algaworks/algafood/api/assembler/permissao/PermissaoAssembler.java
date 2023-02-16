package com.algaworks.algafood.api.assembler.permissao;


import com.algaworks.algafood.api.model.dto.PermissaoDTO;
import com.algaworks.algafood.domain.model.Permissao;
import java.util.Collection;
import java.util.List;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PermissaoAssembler {

    @Autowired
    private ModelMapper modelMapper;

    public PermissaoDTO toDTO(Permissao permissao){
        return modelMapper.map(permissao,PermissaoDTO.class);
    }

    public List<PermissaoDTO> toCollectionDTO(Collection<Permissao> permissoes){

        return permissoes.stream()
                .map(this::toDTO)
                .toList();
    }
}
