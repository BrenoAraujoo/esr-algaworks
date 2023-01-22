package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.assembler.formaPagamento.FormaPagamentoAssembler;
import com.algaworks.algafood.api.assembler.formaPagamento.FormaPagamentoDisassembler;
import com.algaworks.algafood.api.model.dto.FormaPagamentoDTO;
import com.algaworks.algafood.api.model.input.FormaPagamentoInput;
import com.algaworks.algafood.domain.model.FormaPagamento;
import com.algaworks.algafood.domain.model.repository.FormaPagamentoRepository;
import com.algaworks.algafood.domain.service.CadastroFormaPagamentoService;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/formasPagamento")
public class FormaPagamentoController {

    @Autowired
    private CadastroFormaPagamentoService formaPagamentoService;

    @Autowired
    private FormaPagamentoRepository formaPagamentoRepository;

    @Autowired
    private FormaPagamentoAssembler formaPagamentoAssembler;

    @Autowired
    private FormaPagamentoDisassembler formaPagamentoDisassembler;

    @GetMapping
    public List<FormaPagamentoDTO> listar() {
        return formaPagamentoAssembler.toCollectionDTO(formaPagamentoRepository.findAll());
    }

    @GetMapping("/{id}")
    public FormaPagamentoDTO buscar(@PathVariable Long id) {
        return formaPagamentoAssembler.toDTO(formaPagamentoService.buscarOuFalhar(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public FormaPagamentoDTO adicionar(@RequestBody @Valid FormaPagamentoInput formaPagamentoInput) {
        var formaPagamento = formaPagamentoDisassembler.toDomainObject(formaPagamentoInput);
        return formaPagamentoAssembler.toDTO(formaPagamentoService.salvar(formaPagamento));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long id) {
        formaPagamentoService.remover(id);
    }


    @PutMapping("/{id}")
    public FormaPagamento atualizar(@PathVariable Long id, @RequestBody FormaPagamentoInput formaPagamentoInput) {

        var formaPagamentoAtual = formaPagamentoService.buscarOuFalhar(id);
        formaPagamentoDisassembler.copyFromInputToDomain(formaPagamentoInput,formaPagamentoAtual);
        return formaPagamentoService.salvar(formaPagamentoAtual);

    }

}
