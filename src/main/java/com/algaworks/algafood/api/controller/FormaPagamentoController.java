package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.domain.model.FormaPagamento;
import com.algaworks.algafood.domain.model.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.model.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.model.repository.FormaPagamentoRepository;
import com.algaworks.algafood.domain.service.CadastroFormaPagamentoService;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/formasPagamento")
public class FormaPagamentoController {

    @Autowired
    private CadastroFormaPagamentoService formaPagamentoService;

    @Autowired
    private FormaPagamentoRepository formaPagamentoRepository;

    @GetMapping
    public List<FormaPagamento> listar() {
        return formaPagamentoRepository.findAll();
    }

    @GetMapping("/{id}")
    public FormaPagamento buscar(@PathVariable Long id) {
        return formaPagamentoService.buscarOuFalhar(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public FormaPagamento adicionar(@RequestBody FormaPagamento formaPagamento) {
        return formaPagamentoService.salvar(formaPagamento);
    }

    @DeleteMapping("/{id}")
    public void remover(@PathVariable Long id) {
        formaPagamentoService.remover(id);
    }


    @PutMapping("/{id}")
    public FormaPagamento atualizar(@PathVariable Long id, @RequestBody FormaPagamento formaPagamento) {

        var formaPagamentoAtual = formaPagamentoService.buscarOuFalhar(id);
        BeanUtils.copyProperties(formaPagamento, formaPagamentoAtual, "id");
        var formaPagamentoSalvo = formaPagamentoService.salvar(formaPagamentoAtual);
        return formaPagamentoSalvo;

    }

}
