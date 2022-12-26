package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.domain.model.Permissao;
import com.algaworks.algafood.domain.model.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.model.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.model.exception.FormaPagamentoNaoEncontradaException;
import com.algaworks.algafood.domain.model.repository.PermissaoRepository;
import com.algaworks.algafood.domain.service.CadastroPermissaoService;
import com.mysql.cj.log.Log;
import java.util.List;
import javax.validation.Valid;
import org.apache.coyote.Response;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/permissoes")
public class PermissaoController {

    @Autowired
    private PermissaoRepository permissaoRepository;
    @Autowired
    private CadastroPermissaoService permissaoService;

    @GetMapping
    public List<Permissao> listar() {
        return permissaoRepository.findAll();
    }

    @GetMapping("/{id}")
    public Permissao buscar(@PathVariable Long id) {
        return permissaoService.buscarOuFalhar(id);

    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Permissao adicionar(@RequestBody @Valid Permissao permissao) {
        return permissaoService.salvar(permissao);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long id) {
        permissaoService.remover(id);
    }

    @PutMapping("/{id}")
    public Permissao atualizar(@PathVariable Long id, @RequestBody Permissao permissao) {
        var permissaoAtual = permissaoService.buscarOuFalhar(id);

        BeanUtils.copyProperties(permissao, permissaoAtual, "id");
        var permissaoSalva = permissaoRepository.save(permissaoAtual);
        return permissaoSalva;
    }
}
