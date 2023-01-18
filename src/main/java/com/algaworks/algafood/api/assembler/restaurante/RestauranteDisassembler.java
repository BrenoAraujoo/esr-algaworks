package com.algaworks.algafood.api.assembler.restaurante;


import com.algaworks.algafood.api.model.input.RestauranteInput;
import com.algaworks.algafood.domain.model.Restaurante;
import javax.persistence.EntityManager;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RestauranteDisassembler {


    @Autowired
    EntityManager entityManager;

    @Autowired
    private ModelMapper modelMapper;

    public Restaurante toDomainObject(RestauranteInput restauranteInput) {
        return modelMapper.map(restauranteInput, Restaurante.class);
    }

    public void copyFromInputToDomainObject(RestauranteInput restauranteInput, Restaurante restaurante) {
        entityManager.detach(restaurante.getCozinha());
        modelMapper.map(restauranteInput, restaurante);
        entityManager.clear();
    }
}
