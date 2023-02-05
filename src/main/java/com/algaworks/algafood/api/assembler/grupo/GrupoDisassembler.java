package com.algaworks.algafood.api.assembler.grupo;


import com.algaworks.algafood.api.model.dtoinput.GrupoInput;
import com.algaworks.algafood.domain.model.Grupo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GrupoDisassembler {

    @Autowired
    private ModelMapper modelMapper;

    public Grupo toDomainObject(GrupoInput grupoInput){
        return modelMapper.map(grupoInput,Grupo.class);
    }

    public void copyFromInputToDomainObject(GrupoInput grupoInput, Grupo grupo){
        modelMapper.map(grupoInput,grupo);
    }
}
