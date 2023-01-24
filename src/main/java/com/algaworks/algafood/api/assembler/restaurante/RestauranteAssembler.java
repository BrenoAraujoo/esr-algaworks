package com.algaworks.algafood.api.assembler.restaurante;

import com.algaworks.algafood.api.model.dto.RestauranteDTO;
import com.algaworks.algafood.domain.model.Restaurante;
import java.util.List;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RestauranteAssembler {

    @Autowired
    private ModelMapper modelMapper;



    public  RestauranteDTO toDTO(Restaurante restaurante){

        return modelMapper.map(restaurante,RestauranteDTO.class);
    }

    public List<RestauranteDTO> toCollectionDTO(List<Restaurante> restaurantes){
        return restaurantes.stream()
                .map(this::toDTO)
                .toList();
    }


}
