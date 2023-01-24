package com.algaworks.algafood.api.assembler.produto;

import com.algaworks.algafood.api.model.dtoinput.ProdutoInput;
import com.algaworks.algafood.domain.model.Produto;
import javax.persistence.EntityManager;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProdutoDisassembler {

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private ModelMapper modelMapper;

    public Produto toDomainObject(ProdutoInput produtoInput){
        return modelMapper.map(produtoInput,Produto.class);
    }
    public void copyFromInputToDomainObject(ProdutoInput produtoInput, Produto produto){
        entityManager.detach(produto.getRestaurante());
        modelMapper.map(produtoInput,produto);
        entityManager.clear();

    }
}
