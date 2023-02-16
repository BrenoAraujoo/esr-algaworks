package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.assembler.grupo.GrupoAssembler;
import com.algaworks.algafood.api.assembler.permissao.PermissaoAssembler;
import com.algaworks.algafood.api.model.dto.PermissaoDTO;
import com.algaworks.algafood.domain.service.CadastroGrupoService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/grupos/{grupoId}/permissoes",
        produces = MediaType.APPLICATION_JSON_VALUE)
public class GrupoPermissaoController {


    @Autowired
    private CadastroGrupoService grupoService;
    @Autowired
    private GrupoAssembler grupoAssembler;

    @Autowired
    private PermissaoAssembler permissaoAssembler;


    @GetMapping
    private List<PermissaoDTO> listar(@PathVariable Long grupoId) {
        var grupo = grupoService.buscarOufalhar(grupoId);
        var permissoes = grupo.getPermissoes();

        return permissaoAssembler.toCollectionDTO(permissoes);
    }


    @PutMapping("/{permissaoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void associarPermissao(@PathVariable Long grupoId, @PathVariable Long permissaoId) {
        grupoService.associarPermissao(grupoId, permissaoId);
    }

    @DeleteMapping("/{permissaoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void desassociarPermissao(@PathVariable Long grupoId, @PathVariable Long permissaoId) {
        grupoService.desaassociarPermissao(grupoId, permissaoId);
    }

}
