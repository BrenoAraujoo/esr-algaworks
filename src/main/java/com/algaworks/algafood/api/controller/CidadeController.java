package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.assembler.cidade.CidadeAssembler;
import com.algaworks.algafood.api.assembler.cidade.CidadeDisassembler;
import com.algaworks.algafood.api.model.dto.CidadeDTO;
import com.algaworks.algafood.api.model.input.CidadeInput;
import com.algaworks.algafood.api.model.views.Views;
import com.algaworks.algafood.domain.model.exception.EstadoNaoEncontradoException;
import com.algaworks.algafood.domain.model.exception.NegocioException;
import com.algaworks.algafood.domain.model.repository.CidadeRepository;
import com.algaworks.algafood.domain.service.CadastroCidadeService;
import com.fasterxml.jackson.annotation.JsonView;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cidades")
public class CidadeController {

    @Autowired
    private CidadeRepository cidadeRepository;
    @Autowired
    private CadastroCidadeService cidadeService;

    @Autowired
    private CidadeAssembler cidadeAssembler;

    @Autowired
    private CidadeDisassembler cidadeDisassembler;


    @GetMapping
//    @JsonView(Views.Public.class)
    public List<CidadeDTO> listar() {
        return cidadeAssembler.toCollectionDTO(cidadeRepository.findAll());
    }

//    @JsonView(Views.Internal.class)
    @GetMapping("/{id}")
    public CidadeDTO buscar(@PathVariable Long id) {
        return cidadeAssembler.toDTO(cidadeService.buscarOuFalhar(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CidadeDTO salvar(@RequestBody @Valid CidadeInput cidadeInput) {
        try {
            var cidade = cidadeDisassembler.toDomainObject(cidadeInput);
            return cidadeAssembler.toDTO( cidadeService.salvar(cidade));

        } catch (EstadoNaoEncontradoException e) {
            throw new NegocioException(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public CidadeDTO atualizar(@PathVariable Long id, @RequestBody @Valid CidadeInput cidadeInput) {

        var cidadeAtual = cidadeService.buscarOuFalhar(id);
        cidadeDisassembler.copyFromInputtoDomainModel(cidadeInput, cidadeAtual);
//        BeanUtils.copyProperties(cidade, cidadeAtual, "id");
        try {
            return cidadeAssembler.toDTO(cidadeService.salvar(cidadeAtual));
        } catch (EstadoNaoEncontradoException e) {
            throw new NegocioException(e.getMessage(), e);
        }
    }


    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long id) {
        cidadeService.remover(id);
    }


}
