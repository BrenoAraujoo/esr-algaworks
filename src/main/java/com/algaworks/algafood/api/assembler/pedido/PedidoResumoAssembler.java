package com.algaworks.algafood.api.assembler.pedido;


import com.algaworks.algafood.api.model.dto.PedidoDTO;
import com.algaworks.algafood.api.model.dto.PedidoResumoDTO;
import com.algaworks.algafood.domain.model.Pedido;
import java.util.Collection;
import java.util.List;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PedidoResumoAssembler {

    @Autowired
    private ModelMapper mapper;


    public PedidoResumoDTO toDTO(Pedido pedido){
        return mapper.map(pedido, PedidoResumoDTO.class);
    }


    public List<PedidoResumoDTO> toCollectionDTO(Collection<Pedido> pedidos){
        return pedidos.stream()
                .map(this::toDTO)
                .toList();
    }


}
