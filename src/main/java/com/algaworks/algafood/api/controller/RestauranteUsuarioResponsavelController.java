package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.assembler.usuario.UsuarioAssembler;
import com.algaworks.algafood.api.model.dto.UsuarioDTO;
import com.algaworks.algafood.domain.service.CadastroRestauranteService;
import com.algaworks.algafood.domain.service.CadastroUsuarioService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "restaurantes/{restauranteId}/responsaveis",
produces = MediaType.APPLICATION_JSON_VALUE)
public class RestauranteUsuarioResponsavelController {

    @Autowired
    private CadastroRestauranteService restauranteService;

    @Autowired
    private CadastroUsuarioService usuarioService;
    @Autowired
    private UsuarioAssembler usuarioAssembler;

    @GetMapping
    public List<UsuarioDTO> listar(@PathVariable Long restauranteId){
       var restaurante = restauranteService.buscarOuFalhar(restauranteId);
       return usuarioAssembler.toCollectionDTO(restaurante.getResponsaveis());
    }

    @PutMapping("/{usuarioId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void associarUsuario(@PathVariable Long restauranteId, @PathVariable Long usuarioId){
        restauranteService.associarResponsavel(restauranteId,usuarioId);
    }

    @DeleteMapping("/{usuarioId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void desaassociarUsuario(@PathVariable Long restauranteId, @PathVariable Long usuarioId){
    restauranteService.desaassociarResponsavel(restauranteId, usuarioId);
    }
}
