package com.algaworks.algafood.api.assembler.formaPagamento;

import com.algaworks.algafood.api.model.dtoinput.FormaPagamentoInput;
import com.algaworks.algafood.domain.model.FormaPagamento;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FormaPagamentoDisassembler {

    @Autowired
    private ModelMapper modelMapper;

    public FormaPagamento toDomainObject(FormaPagamentoInput formaPagamentoInput){
        return modelMapper.map(formaPagamentoInput,FormaPagamento.class);
    }
    public void copyFromInputToDomain(FormaPagamentoInput formaPagamentoInput, FormaPagamento formaPagamento){
        modelMapper.map(formaPagamentoInput,formaPagamento);
    }
}
