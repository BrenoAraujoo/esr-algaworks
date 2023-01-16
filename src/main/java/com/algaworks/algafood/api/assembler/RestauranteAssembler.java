package com.algaworks.algafood.api.assembler;

import com.algaworks.algafood.api.model.dto.RestauranteDTO;
import com.algaworks.algafood.domain.model.Restaurante;
import java.util.Comparator;
import java.util.List;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RestauranteAssembler {

    @Autowired
    private ModelMapper modelMapper;



    public  RestauranteDTO toDTO(Restaurante restaurante){

//        TypeMap<Restaurante, RestauranteDTO> typeMap = modelMapper.createTypeMap(Restaurante.class, RestauranteDTO.class);
//        typeMap.addMapping(Restaurante::getNome,RestauranteDTO::setNome);
        return modelMapper.map(restaurante,RestauranteDTO.class);
    }

    public List<RestauranteDTO> toCollectionDTO(List<Restaurante> restaurantes){

        Comparator<RestauranteDTO> comparator = (restaurante1, restaurante2) ->
                restaurante1.getId().compareTo(restaurante2.getId());

        return restaurantes.stream()
                .map(this::toDTO)
                .sorted(comparator)
                .toList();
    }
}