package com.algaworks.algafood.api.assembler.estado;

import com.algaworks.algafood.api.model.dto.EstadoDTO;
import com.algaworks.algafood.domain.model.Estado;
import java.util.List;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EstadoAssembler {

    @Autowired
    private ModelMapper modelMapper;

    public EstadoDTO toDTO(Estado estado){
        return modelMapper.map(estado,EstadoDTO.class);
    }

    public List<EstadoDTO> toCollectionDTO(List<Estado> estados){
        return estados.stream()
                .map(estado -> toDTO(estado))
                .toList();
    }
}
