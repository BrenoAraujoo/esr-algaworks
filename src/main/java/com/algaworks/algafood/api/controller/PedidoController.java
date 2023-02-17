package com.algaworks.algafood.api.controller;


import com.algaworks.algafood.api.assembler.pedido.PedidoAssembler;
import com.algaworks.algafood.api.assembler.pedido.PedidoResumoAssembler;
import com.algaworks.algafood.api.model.dto.PedidoDTO;
import com.algaworks.algafood.api.model.dto.PedidoResumoDTO;
import com.algaworks.algafood.domain.model.repository.PedidoRepository;
import com.algaworks.algafood.domain.service.EmissaoPedidoService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {


    @Autowired
    private PedidoAssembler pedidoAssembler;

    @Autowired
    private PedidoResumoAssembler pedidoResumoAssembler;

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private EmissaoPedidoService pedidoService;

    @GetMapping
    public List<PedidoResumoDTO> listar(){
        var pedidos = pedidoRepository.findAll();
        return pedidoResumoAssembler.toCollectionDTO(pedidos);
    }

    @GetMapping("/{pedidoId}")
    public PedidoDTO buscar(@PathVariable Long pedidoId){
        var pedido = pedidoService.buscarOuFalhar(pedidoId);
        return pedidoAssembler.toDTO(pedido);
    }

}
