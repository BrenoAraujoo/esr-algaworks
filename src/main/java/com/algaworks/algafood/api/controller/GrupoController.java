package com.algaworks.algafood.api.controller;


import com.algaworks.algafood.api.assembler.grupo.GrupoAssembler;
import com.algaworks.algafood.api.assembler.grupo.GrupoDisassembler;
import com.algaworks.algafood.api.model.dto.GrupoDTO;
import com.algaworks.algafood.api.model.dtoinput.GrupoInput;
import com.algaworks.algafood.domain.model.repository.GrupoRepository;
import com.algaworks.algafood.domain.service.CadastroGrupoService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/grupos")
public class GrupoController {


    @Autowired
    private GrupoRepository grupoRepository;

    @Autowired
    private CadastroGrupoService grupoService;

    @Autowired
    private GrupoAssembler grupoAssembler;

    @Autowired
    private GrupoDisassembler grupoDisassembler;

    @GetMapping
    public List<GrupoDTO> listar(){
        return grupoAssembler.listToDTO(grupoRepository.findAll());
    }
    @GetMapping("/{id}")
    public GrupoDTO buscar(@PathVariable Long id){
        return grupoAssembler.toDTO(grupoService.buscarOufalhar(id));
    }

    @PostMapping
    public GrupoDTO salvar(@RequestBody GrupoInput grupoInput){
        var grupo = grupoDisassembler.toDomainObject(grupoInput);
        return grupoAssembler.toDTO(grupoService.salvar(grupo));

    }
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public GrupoDTO atualizar(@PathVariable Long id, @RequestBody GrupoInput grupoInput){

        var grupoAtual = grupoService.buscarOufalhar(id);

        grupoDisassembler.copyFromInputToDomainObject(grupoInput,grupoAtual);

        return grupoAssembler.toDTO(grupoService.salvar(grupoAtual));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long id){
        grupoService.remover(id);
    }
}
