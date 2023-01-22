package com.algaworks.algafood.api.controller;


import com.algaworks.algafood.api.assembler.produto.ProdutoAssembler;
import com.algaworks.algafood.api.assembler.produto.ProdutoDisassembler;
import com.algaworks.algafood.api.model.dto.ProdutoDTO;
import com.algaworks.algafood.api.model.input.ProdutoInput;
import com.algaworks.algafood.domain.model.Produto;
import com.algaworks.algafood.domain.model.exception.NegocioException;
import com.algaworks.algafood.domain.model.exception.ProdutoNaoEncontratoException;
import com.algaworks.algafood.domain.model.repository.ProdutoRespository;
import com.algaworks.algafood.domain.service.CadastroProdutoService;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {

    @Autowired
    private ProdutoRespository produtoRespository;

    @Autowired
    private CadastroProdutoService produtoService;

    @Autowired
    private ProdutoAssembler produtoAssembler;

    @Autowired
    private ProdutoDisassembler produtoDisassembler;

    @GetMapping
    public List<ProdutoDTO> listar() {
        return produtoAssembler.toCollectionDTO(produtoRespository.findAll());
    }

    @GetMapping("/{id}")
    public ProdutoDTO buscar(@PathVariable Long id) {
        return produtoAssembler.toDTO(produtoService.buscarOuFalhar(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProdutoDTO salvar(@RequestBody @Valid ProdutoInput produtoInput) {
        var produto = produtoDisassembler.toDomainObject(produtoInput);
        return produtoAssembler.toDTO(produtoService.salvar(produto));

    }

    @PutMapping("/{id}")
    public ProdutoDTO atualizar(@PathVariable Long id, @RequestBody ProdutoInput produtoInput) {
        var produtoAtual = produtoService.buscarOuFalhar(id);

        produtoDisassembler.copyFromInputToDomainObject(produtoInput, produtoAtual);
        try {
            return produtoAssembler.toDTO(produtoService.salvar(produtoAtual));
        } catch (ProdutoNaoEncontratoException e) {
            throw new NegocioException(e.getMessage(), e);
        }
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long id) {
        produtoService.remover(id);
    }
}
