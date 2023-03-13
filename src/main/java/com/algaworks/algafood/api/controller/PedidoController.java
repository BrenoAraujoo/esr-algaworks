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
import com.algaworks.algafood.infrastructure.repository.filter.PedidoFilter;
import com.algaworks.algafood.infrastructure.repository.spec.PedidoSpecs;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
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

//    @GetMapping
//    public List<PedidoResumoDTO> pesquisar(PedidoFilter filtro){
//
//        var pedidos = pedidoRepository.findAll(PedidoSpecs.usandoFiltro(filtro));
//        return pedidoResumoAssembler.toCollectionDTO(pedidos);
//    }


//    @GetMapping
//    public Page<PedidoResumoDTO> pesquisar(PedidoFilter filtro, Pageable pageable){
//
//        var pedidosPage =
//                pedidoRepository.findAll(PedidoSpecs.usandoFiltro(filtro), pageable);
//        var pedidoDTO = pedidoResumoAssembler.toCollectionDTO(pedidosPage.getContent());
//        var pedidoPageDTO = new PageImpl<>(pedidoDTO,pageable,pedidosPage.getTotalElements());
//        return pedidoPageDTO;
//    }

    @GetMapping
    public Page<PedidoResumoDTO> pesquisar(PedidoFilter filtro, Pageable pageable){
        var pedidosPage =
                pedidoRepository.findAll(PedidoSpecs.usandoFiltro(filtro), pageable);
        var pedidoDTO = pedidoResumoAssembler.toCollectionDTO(pedidosPage.getContent());
        var pedidoPageDTO = new PageImpl<>(pedidoDTO,pageable,pedidosPage.getTotalElements());
        return pedidoPageDTO;
    }




//    @GetMapping
//    public MappingJacksonValue listar(@RequestParam(required = false) String campos){
//        var pedidos = pedidoRepository.findAll();
//        var pedidosDTO = pedidoAssembler.toCollectionDTO(pedidos);
//        MappingJacksonValue pedidosWrapper = new MappingJacksonValue(pedidosDTO);
//
//        SimpleFilterProvider filterProvider = new SimpleFilterProvider();
//        filterProvider.addFilter("pedidoFilter", SimpleBeanPropertyFilter.serializeAll());
//
//        if(StringUtils.isNotBlank(campos)){
//            filterProvider.addFilter
//                ("pedidoFilter", SimpleBeanPropertyFilter.filterOutAllExcept(campos.split(",")));
//        }
//
//        pedidosWrapper.setFilters(filterProvider);
//
//        return pedidosWrapper;
//    }

    @GetMapping("/{codigoPedido}")
    public PedidoDTO buscar(@PathVariable String codigoPedido){
        var pedido = pedidoService.buscarOuFalhar(codigoPedido);
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
