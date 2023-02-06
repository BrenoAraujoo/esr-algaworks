package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.assembler.permissao.PermissaoAssembler;
import com.algaworks.algafood.api.assembler.permissao.PermissaoDisassembler;
import com.algaworks.algafood.api.model.dto.PermissaoDTO;
import com.algaworks.algafood.api.model.dtoinput.PermissaoInput;
import com.algaworks.algafood.domain.model.repository.PermissaoRepository;
import com.algaworks.algafood.domain.service.CadastroPermissaoService;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/permissoes")
public class PermissaoController {

    @Autowired
    private PermissaoRepository permissaoRepository;
    @Autowired
    private CadastroPermissaoService permissaoService;

    @Autowired
    private PermissaoAssembler permissaoAssembler;

    @Autowired
    private PermissaoDisassembler permissaoDisassembler;

    @GetMapping
    public List<PermissaoDTO> listar() {
        return permissaoAssembler.toCollectionDTO(permissaoRepository.findAll());
    }

    @GetMapping("/{id}")
    public PermissaoDTO buscar(@PathVariable Long id) {
        return permissaoAssembler.toDTO(permissaoService.buscarOuFalhar(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PermissaoDTO adicionar(@RequestBody @Valid PermissaoInput permissaoInput) {
            var permissao = permissaoDisassembler.toDomainObject(permissaoInput);
            return permissaoAssembler.toDTO(permissaoService.salvar(permissao));

    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public PermissaoDTO atualizar(@PathVariable Long id, @RequestBody PermissaoInput permissaoInput) {
        var permissaoAtual = permissaoService.buscarOuFalhar(id);
        permissaoDisassembler.copyFromInputToDomain(permissaoInput,permissaoAtual);
        var permissaoSalva = permissaoAssembler.toDTO(permissaoRepository.save(permissaoAtual));
        return permissaoSalva;
    }


    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long id) {
        permissaoService.remover(id);
    }
}
