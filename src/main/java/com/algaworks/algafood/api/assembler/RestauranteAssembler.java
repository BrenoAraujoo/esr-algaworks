package com.algaworks.algafood.api.assembler;

import com.algaworks.algafood.api.model.dto.CozinhaDTO;
import com.algaworks.algafood.api.model.dto.RestauranteDTO;
import com.algaworks.algafood.domain.model.Restaurante;
import java.util.Comparator;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class RestauranteAssembler {

    public  RestauranteDTO toDTO(Restaurante restaurante){
        RestauranteDTO restauranteDTO = new RestauranteDTO();
        CozinhaDTO cozinhaDTO = new CozinhaDTO();

        cozinhaDTO.setId(restaurante.getCozinha().getId());
        cozinhaDTO.setNome(restaurante.getCozinha().getNome());

        restauranteDTO.setId(restaurante.getId());
        restauranteDTO.setNome(restaurante.getNome());
        restauranteDTO.setCozinha(cozinhaDTO);
        restauranteDTO.setTaxaFrete(restaurante.getTaxaFrete());

        return restauranteDTO;
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
