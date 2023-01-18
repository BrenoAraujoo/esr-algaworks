package com.algaworks.algafood.core.modelmapper;


import com.algaworks.algafood.api.model.dto.CidadeDTO;
import com.algaworks.algafood.api.model.dto.RestauranteDTO;
import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.model.Restaurante;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {
    @Bean
    public ModelMapper modelMapper(){
       var modelMapper = new ModelMapper();

//       Configura as correspondencias de uma classe em outra
//       modelMapper.typeMap(Restaurante.class, RestauranteDTO.class)
//               .addMappings(mapping -> {
//                   mapping.map(Restaurante::getNome,RestauranteDTO::setNomeDTO);
//                   mapping.map(Restaurante::getId, RestauranteDTO::setId);
//               });

        return modelMapper;
    }
}
