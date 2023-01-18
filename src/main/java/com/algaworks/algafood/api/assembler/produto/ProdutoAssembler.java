package com.algaworks.algafood.api.assembler.produto;

import com.algaworks.algafood.api.model.dto.ProdutoDTO;
import com.algaworks.algafood.domain.model.Produto;
import java.util.List;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProdutoAssembler {

    @Autowired
    private ModelMapper modelMapper;

    public ProdutoDTO toDTO(Produto produto){
        return modelMapper.map(produto,ProdutoDTO.class);
    }

    public List<ProdutoDTO> toCollectionDTO(List<Produto> produtos){
        return produtos.stream()
                .map(produto -> toDTO(produto))
                .toList();
    }
}
