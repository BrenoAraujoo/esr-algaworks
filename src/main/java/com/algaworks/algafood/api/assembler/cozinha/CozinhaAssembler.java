package com.algaworks.algafood.api.assembler.cozinha;


import com.algaworks.algafood.api.model.dto.CozinhaDTO;
import com.algaworks.algafood.domain.model.Cozinha;
import java.util.List;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CozinhaAssembler {

    @Autowired
    private ModelMapper mapper;

    public CozinhaDTO toDTO(Cozinha cozinha){
        return mapper.map(cozinha,CozinhaDTO.class);
    }
    public List<CozinhaDTO> toCollectionDTO(List<Cozinha> cozinhaList){
        return cozinhaList
                .stream()
                .map(this::toDTO)
                .toList();
    }
}
