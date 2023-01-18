package com.algaworks.algafood.api.assembler.cidade;


import com.algaworks.algafood.api.model.dto.CidadeDTO;
import com.algaworks.algafood.domain.model.Cidade;
import java.util.List;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CidadeAssembler {

    @Autowired
    private ModelMapper modelMapper;


    public CidadeDTO toDTO(Cidade cidade){
        return modelMapper.map(cidade,CidadeDTO.class);

    }

    public List<CidadeDTO> toCollectionDTO(List<Cidade> cidades){
        return cidades.stream()
                .map(cidade -> toDTO(cidade))
                .toList();
    }
}
