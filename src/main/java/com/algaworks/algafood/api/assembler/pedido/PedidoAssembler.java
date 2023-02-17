package com.algaworks.algafood.api.assembler.pedido;


import com.algaworks.algafood.api.model.dto.PedidoDTO;
import com.algaworks.algafood.domain.model.Pedido;
import java.util.Collection;
import java.util.List;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PedidoAssembler {

    @Autowired
    private ModelMapper mapper;


    public PedidoDTO toDTO(Pedido pedido){
        return mapper.map(pedido, PedidoDTO.class);
    }

    public List<PedidoDTO> toCollectionDTO(Collection<Pedido> pedidos){
        return pedidos.stream()
                .map(this::toDTO)
                .toList();
    }
}
