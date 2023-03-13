package com.algaworks.algafood.api.assembler.cozinha;

import com.algaworks.algafood.api.model.dtoinput.CozinhaInput;
import com.algaworks.algafood.domain.model.Cozinha;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CozinhaDisassembler {

    @Autowired
    private ModelMapper mapper;

    public Cozinha toDomainObject(CozinhaInput cozinhaInput){
        return mapper.map(cozinhaInput, Cozinha.class);
    }

    public void copyFromInputToDomainModel(CozinhaInput cozinhaInput, Cozinha cozinha){
        mapper.map(cozinhaInput,cozinha);
    }
}
