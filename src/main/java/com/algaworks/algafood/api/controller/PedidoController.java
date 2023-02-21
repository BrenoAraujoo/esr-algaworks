package com.algaworks.algafood.api.controller;


import com.algaworks.algafood.api.assembler.pedido.PedidoAssembler;
import com.algaworks.algafood.api.assembler.pedido.PedidoDesassembler;
import com.algaworks.algafood.api.assembler.pedido.PedidoResumoAssembler;
import com.algaworks.algafood.api.model.dto.PedidoDTO;
import com.algaworks.algafood.api.model.dto.PedidoResumoDTO;
import com.algaworks.algafood.api.model.dtoinput.PedidoInput;
import com.algaworks.algafood.domain.model.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.model.exception.NegocioException;
import com.algaworks.algafood.domain.model.repository.PedidoRepository;
import com.algaworks.algafood.domain.service.EmissaoPedidoService;
import com.algaworks.algafood.domain.service.FluxoPedidoService;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {

    @Autowired
    private PedidoAssembler pedidoAssembler;

    @Autowired
    private PedidoResumoAssembler pedidoResumoAssembler;

    @Autowired
    private PedidoDesassembler pedidoDesassembler;

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private EmissaoPedidoService pedidoService;

    @Autowired
    private FluxoPedidoService fluxoPedidoService;

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

    @PostMapping
    public PedidoDTO adicionar(@RequestBody @Valid PedidoInput pedidoInput){
        try {
            // TODO pegar usuario autenticado

        var novoPedido = pedidoDesassembler.toDomainObject(pedidoInput);
//        novoPedido.setCliente(new Usuario());
//        novoPedido.getCliente().setId(1L);

        novoPedido = pedidoService.emitir(novoPedido);
        return pedidoAssembler.toDTO(pedidoService.emitir(novoPedido));

        }catch (EntidadeNaoEncontradaException ex){
            throw new NegocioException(ex.getMessage());
        }
    }




}
