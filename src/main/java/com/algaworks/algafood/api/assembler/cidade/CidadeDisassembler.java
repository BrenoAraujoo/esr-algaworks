package com.algaworks.algafood.api.assembler.cidade;


import com.algaworks.algafood.api.model.input.CidadeInput;
import com.algaworks.algafood.domain.model.Cidade;
import javax.persistence.EntityManager;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CidadeDisassembler {

    @Autowired
    private EntityManager entityManager;
    @Autowired
    private ModelMapper modelMapper;

    public Cidade toDomainObject(CidadeInput cidadeInput){
        return modelMapper.map(cidadeInput,Cidade.class);

    }

    public void copyFromInputtoDomainModel(CidadeInput cidadeInput, Cidade cidade){
        entityManager.detach(cidade.getEstado());
        modelMapper.map(cidadeInput,cidade);
        entityManager.clear();

    }



}
