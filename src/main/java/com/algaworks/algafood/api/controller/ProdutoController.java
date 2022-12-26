package com.algaworks.algafood.api.controller;


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

    @GetMapping
    public List<Produto> listar() {
        return produtoRespository.findAll();
    }

    @GetMapping("/{id}")
    public Produto buscar(@PathVariable Long id) {
        return produtoService.buscarOuFalhar(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Produto salvar(@RequestBody @Valid Produto produto) {
        return produtoService.salvar(produto);
    }

    @PutMapping("/{id}")
    public Produto atualizar(@PathVariable Long id, @RequestBody Produto produto) {
        var produtoAtual = produtoService.buscarOuFalhar(id);
        BeanUtils.copyProperties(produto, produtoAtual, "id");
        try {
            return produtoService.salvar(produtoAtual);
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
