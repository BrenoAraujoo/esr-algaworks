package com.algaworks.algafood.core.modelmapper;


import com.algaworks.algafood.api.model.dto.EnderecoDTO;
import com.algaworks.algafood.api.model.dtoinput.ItemPedidoInput;
import com.algaworks.algafood.domain.model.Endereco;
import com.algaworks.algafood.domain.model.ItemPedido;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {


    @Bean
    public ModelMapper modelMapper() {
        var modelMapper = new ModelMapper();

        var enderecoToEnderecoDTOTypeMap = modelMapper.createTypeMap(Endereco.class, EnderecoDTO.class);
        enderecoToEnderecoDTOTypeMap.<String>addMapping(
                enderecoSrc -> enderecoSrc.getCidade().getEstado().getNome(),
                (enderecoDest, value) -> enderecoDest.getCidade().setEstado(value)
        );
        modelMapper.createTypeMap(ItemPedidoInput.class, ItemPedido.class)
                .addMappings(mapper -> mapper.skip(ItemPedido::setId));


        return modelMapper;
    }
}
