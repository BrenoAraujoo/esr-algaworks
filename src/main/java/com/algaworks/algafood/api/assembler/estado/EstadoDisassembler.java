package com.algaworks.algafood.api.assembler.estado;


import com.algaworks.algafood.api.model.input.EstadoInput;
import com.algaworks.algafood.domain.model.Estado;
import javax.persistence.EntityManager;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EstadoDisassembler {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private EntityManager entityManager;

    public Estado toDomainObject(EstadoInput estadoInput){
        return modelMapper.map(estadoInput,Estado.class);
    }

    public void copyFromInputToDomainObject(EstadoInput estadoInput, Estado estado){

        modelMapper.map(estadoInput,estado);
//        entityManager.clear();
    }
}
