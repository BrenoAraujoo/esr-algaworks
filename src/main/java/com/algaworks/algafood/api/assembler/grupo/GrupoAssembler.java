package com.algaworks.algafood.api.assembler.grupo;

import com.algaworks.algafood.api.model.dto.GrupoDTO;
import com.algaworks.algafood.domain.model.Grupo;
import java.util.Collection;
import java.util.List;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GrupoAssembler {

    @Autowired
    private ModelMapper modelMapper;

    public GrupoDTO toDTO(Grupo grupo){
        return modelMapper.map(grupo,GrupoDTO.class);
    }

    public List<GrupoDTO> toCollectionDTO(Collection<Grupo> grupos){
        return grupos.stream()
                .map(this::toDTO)
                .toList();
    }

}
