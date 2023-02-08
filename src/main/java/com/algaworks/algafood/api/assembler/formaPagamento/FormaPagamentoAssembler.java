package com.algaworks.algafood.api.assembler.formaPagamento;

import com.algaworks.algafood.api.model.dto.FormaPagamentoDTO;
import com.algaworks.algafood.domain.model.FormaPagamento;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FormaPagamentoAssembler {

    @Autowired
    private ModelMapper modelMapper;

    public FormaPagamentoDTO toDTO(FormaPagamento formaPagamento){
        return modelMapper.map(formaPagamento,FormaPagamentoDTO.class);

    }

    public List<FormaPagamentoDTO> toCollectionDTO(Collection<FormaPagamento> formasPagamento){
        return formasPagamento.stream()
                .map(this::toDTO)
                .toList();
    }
}
