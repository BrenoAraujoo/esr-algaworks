package com.algaworks.algafood.core.modelmapper;


import com.algaworks.algafood.api.model.dto.EnderecoDTO;
import com.algaworks.algafood.api.model.dtoinput.CidadeInput;
import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.model.Endereco;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {

//  Minha implementação
//    @Bean
//    public ModelMapper modelMapper() {
//        var modelMapper = new ModelMapper();
//
//        var enderecoToEnderecoDTOTypeMap = modelMapper.typeMap(Endereco.class,EnderecoDTO.class);
//        enderecoToEnderecoDTOTypeMap.addMapping(
//                enderecoSrc -> enderecoSrc.getCidade().getEstado().getNome(),
//                (enderecoDest, enderecoSrc) -> enderecoDest.getCidade().setEstado((String) enderecoSrc)
//        );
//        return modelMapper;
//    }


    @Bean
    public ModelMapper modelMapper() {
        var modelMapper = new ModelMapper();

        var enderecoToEnderecoDTOTypeMap = modelMapper.createTypeMap(Endereco.class,EnderecoDTO.class);
        enderecoToEnderecoDTOTypeMap.<String>addMapping(
                enderecoSrc -> enderecoSrc.getCidade().getEstado().getNome(),
                (enderecoDest, value) -> enderecoDest.getCidade().setEstado(value)
        );


        return modelMapper;
    }
}
