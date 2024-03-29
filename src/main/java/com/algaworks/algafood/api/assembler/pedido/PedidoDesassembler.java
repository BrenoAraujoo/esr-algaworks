package com.algaworks.algafood.api.assembler.pedido;


import com.algaworks.algafood.api.model.dtoinput.PedidoInput;
import com.algaworks.algafood.domain.model.Pedido;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PedidoDesassembler {

    @Autowired
    private ModelMapper mapper;

    public Pedido toDomainObject(PedidoInput pedidoInput){
        return  mapper.map(pedidoInput,Pedido.class);

    }
    public void copyFromInputToDomain(PedidoInput pedidoInput, Pedido pedido){
        mapper.map(pedidoInput,pedido);
    }
}
