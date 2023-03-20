package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.assembler.estado.EstadoAssembler;
import com.algaworks.algafood.api.assembler.estado.EstadoDisassembler;
import com.algaworks.algafood.api.model.dto.EstadoDTO;
import com.algaworks.algafood.api.model.dtoinput.EstadoInput;
import com.algaworks.algafood.domain.model.Estado;
import com.algaworks.algafood.domain.model.repository.EstadoRepository;
import com.algaworks.algafood.domain.service.CadastroEstadoService;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/estados")
public class EstadoController {

    @Autowired
    private EstadoRepository estadoRespository;

    @Autowired
    private CadastroEstadoService estadoService;

    @Autowired
    private EstadoAssembler estadoAssembler;

    @Autowired
    private EstadoDisassembler estadoDisassembler;

    @GetMapping
    public List<EstadoDTO> listar() {
        return estadoAssembler.toCollectionDTO(estadoRespository.findAll());
    }


    @GetMapping("/{id}")
    public EstadoDTO buscar(@PathVariable Long id) {
        return estadoAssembler.toDTO(estadoService.buscarOuFalhar(id));

    }

    @PostMapping
    public EstadoDTO salvar(@RequestBody @Valid EstadoInput estadoInput) {

        Estado estado = estadoDisassembler.toDomainObject(estadoInput);
        return estadoAssembler.toDTO(estadoService.salvar(estado));

    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public EstadoDTO atualizar(@PathVariable Long id, @RequestBody @Valid EstadoInput estadoInput) {
        var estadoAtual = estadoService.buscarOuFalhar(id);
        estadoDisassembler.copyFromInputToDomainObject(estadoInput, estadoAtual);
        return estadoAssembler.toDTO(estadoService.salvar(estadoAtual));

    }

    @DeleteMapping("/{id}")
    public void remover(@PathVariable Long id) {
        estadoService.remover(id);
    }
}
