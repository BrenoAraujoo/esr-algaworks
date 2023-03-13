package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.assembler.cozinha.CozinhaAssembler;
import com.algaworks.algafood.api.assembler.cozinha.CozinhaDisassembler;
import com.algaworks.algafood.api.model.dto.CozinhaDTO;
import com.algaworks.algafood.api.model.dtoinput.CozinhaInput;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.model.repository.CozinhaRepository;
import com.algaworks.algafood.domain.service.CadastroCozinhaService;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/cozinhas",
        produces = MediaType.APPLICATION_JSON_VALUE)
public class CozinhaController {

    @Autowired
    private CozinhaRepository cozinhaRepository;

    @Autowired
    private CadastroCozinhaService cozinhaService;

    @Autowired
    private CozinhaAssembler cozinhaAssembler;

    @Autowired
    private CozinhaDisassembler cozinhaDisassembler;

    @GetMapping
    public Page<CozinhaDTO> listar( @PageableDefault(size = 10) Pageable pageable) {

        var cozinhasPage =  cozinhaRepository.findAll(pageable);
        var cozinhaDTO = cozinhaAssembler.toCollectionDTO(cozinhasPage.getContent());
        var cozinhaPageDTO =
                new PageImpl<>(cozinhaDTO,pageable,cozinhasPage.getTotalElements());

        return cozinhaPageDTO;
    }

    @GetMapping("/{id}")
    public Cozinha buscar(@PathVariable Long id) {
       return cozinhaService.buscarOuFalhar(id);
    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Cozinha adicionar(@RequestBody @Valid CozinhaInput cozinhaInput) {
        var cozinha = cozinhaDisassembler.toDomainObject(cozinhaInput);
        return cozinhaService.salvar(cozinha);
    }

    @PutMapping("/{id}")
    public Cozinha atualizar(@PathVariable Long id, @RequestBody CozinhaInput cozinhaInput) {
        Cozinha cozinhaAtual = cozinhaService.buscarOuFalhar(id);
        cozinhaDisassembler.copyFromInputToDomainModel(cozinhaInput,cozinhaAtual);
        cozinhaService.salvar(cozinhaAtual);
        return cozinhaAtual;
    }


    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long id) {
            cozinhaService.remover(id);

        }
    }
