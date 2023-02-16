package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.assembler.grupo.GrupoAssembler;
import com.algaworks.algafood.api.model.dto.GrupoDTO;
import com.algaworks.algafood.domain.service.CadastroGrupoService;
import com.algaworks.algafood.domain.service.CadastroUsuarioService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/usuarios/{usuarioId}/grupos",
        produces = MediaType.APPLICATION_JSON_VALUE)
public class UsuarioGrupoController {

    @Autowired
    private CadastroGrupoService grupoService;

    @Autowired
    private GrupoAssembler grupoAssembler;
    @Autowired
    private CadastroUsuarioService usuarioService;


    @GetMapping
    public List<GrupoDTO> listar(@PathVariable Long usuarioId){
        var usuario = usuarioService.buscarOuFalhar(usuarioId);
        return grupoAssembler.toCollectionDTO(usuario.getGrupos());
    }

    @PutMapping("/{grupoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void associarGrupo(@PathVariable Long usuarioId, @PathVariable Long grupoId){
        usuarioService.associarGrupo(usuarioId, grupoId);
    }

    @DeleteMapping("/{grupoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void desaassociarGrupo(@PathVariable Long usuarioId, @PathVariable Long grupoId){
        usuarioService.desaassociarGrupo(usuarioId, grupoId);
    }
}
