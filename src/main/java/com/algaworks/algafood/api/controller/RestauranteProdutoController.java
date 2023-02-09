package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.assembler.formaPagamento.FormaPagamentoAssembler;
import com.algaworks.algafood.api.assembler.produto.ProdutoAssembler;
import com.algaworks.algafood.api.assembler.produto.ProdutoDisassembler;
import com.algaworks.algafood.api.model.dto.FormaPagamentoDTO;
import com.algaworks.algafood.api.model.dto.ProdutoDTO;
import com.algaworks.algafood.api.model.dtoinput.ProdutoInput;
import com.algaworks.algafood.domain.model.Produto;
import com.algaworks.algafood.domain.model.repository.ProdutoRespository;
import com.algaworks.algafood.domain.model.repository.RestauranteRepository;
import com.algaworks.algafood.domain.service.CadastroProdutoService;
import com.algaworks.algafood.domain.service.CadastroRestauranteService;
import java.util.List;
import java.util.Optional;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/restaurantes/{restauranteId}/produtos",
        produces = MediaType.APPLICATION_JSON_VALUE)
public class RestauranteProdutoController {

    @Autowired
    private CadastroRestauranteService restauranteService;

    @Autowired
    private CadastroProdutoService produtoService;

    @Autowired
    private ProdutoRespository produtoRespository;

    @Autowired
    private ProdutoAssembler produtoAssembler;

    @Autowired
    private ProdutoDisassembler produtoDisassembler;
    @GetMapping
    public List<ProdutoDTO> listarPorRestaurante(@PathVariable Long restauranteId){
        var restaurante = restauranteService.buscarOuFalhar(restauranteId);
        var produtos = produtoRespository.findByRestaurante(restaurante);
        return produtoAssembler.toCollectionDTO(produtos);
    }

    @GetMapping("/{produtoId}")
    public ProdutoDTO buscarPordutoPorRestaurante(@PathVariable Long produtoId, @PathVariable Long restauranteId){
        var produto = produtoService.buscarOuFalhar(produtoId, restauranteId);

        return produtoAssembler.toDTO(produto);
    }

    @PostMapping
    public ProdutoDTO salvar(@PathVariable Long restauranteId, @Valid @RequestBody ProdutoInput produtoInput){
        var produto = produtoDisassembler.toDomainObject(produtoInput);
        var restaurante = restauranteService.buscarOuFalhar(restauranteId);

        produto.setRestaurante(restaurante);

        return produtoAssembler.toDTO(produtoService.salvar(produto));
    }

    @PutMapping("/{produtoId}")
    public ProdutoDTO atualizar
            (@Valid @RequestBody ProdutoInput produtoInput, @PathVariable Long produtoId, @PathVariable Long restauranteId){
        var produto = produtoService.buscarOuFalhar(produtoId,restauranteId);
        produtoDisassembler.copyFromInputToDomainObject(produtoInput,produto);
        produtoService.salvar(produto);
        return produtoAssembler.toDTO(produto);
    }

    @PutMapping("/{produtoId}/ativar")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void ativar(@PathVariable Long restauranteId, @PathVariable Long produtoId){
        var produto = produtoService.buscarOuFalhar(produtoId,restauranteId);
        produtoService.ativarProduto(produto);
    }

    @DeleteMapping("/{produtoId}/desativar")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void desativar(@PathVariable Long restauranteId, @PathVariable Long produtoId){
        var produto = produtoService.buscarOuFalhar(produtoId,restauranteId);
        produtoService.desativarProduto(produto);
    }
}
