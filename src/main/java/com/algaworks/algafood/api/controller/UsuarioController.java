package com.algaworks.algafood.api.controller;


import com.algaworks.algafood.api.assembler.usuario.UsuarioAssembler;
import com.algaworks.algafood.api.assembler.usuario.UsuarioDisassembler;
import com.algaworks.algafood.api.model.dto.UsuarioDTO;
import com.algaworks.algafood.api.model.dtoinput.UsuarioAtualizarInput;
import com.algaworks.algafood.api.model.dtoinput.UsuarioInput;
import com.algaworks.algafood.api.model.dtoinput.UsuarioSenhaDTO;
import com.algaworks.algafood.domain.model.Usuario;
import com.algaworks.algafood.domain.service.CadastroUsuarioService;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private CadastroUsuarioService usuarioService;

    @Autowired
    private UsuarioAssembler usuarioAssembler;

    @Autowired
    private UsuarioDisassembler usuarioDisassembler;

    @GetMapping
    public List<UsuarioDTO> listar(){
        List<Usuario> usuarios = usuarioService.listar();
        return usuarioAssembler.listToDTO(usuarios);
    }

    @GetMapping("/{usuarioId}")
    public UsuarioDTO buscar(@PathVariable ("usuarioId") Long id){
        return usuarioAssembler.toDTO(usuarioService.buscar(id));
    }

    @PostMapping
    public UsuarioDTO salvar(@RequestBody @Valid UsuarioInput usuarioInput){

        var usuario = usuarioDisassembler.toDomainObject(usuarioInput);

        return usuarioAssembler.toDTO(usuarioService.salvar(usuario));
    }

    @PutMapping("/{usuarioId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public UsuarioDTO atualizar
            (@RequestBody @Valid UsuarioAtualizarInput usuarioInput, @PathVariable("usuarioId") Long id){
        var usuarioAtual = usuarioService.buscarOuFalhar(id);
        usuarioDisassembler.copyFromInputToDomain(usuarioInput, usuarioAtual);
        return usuarioAssembler.toDTO(usuarioService.salvar(usuarioAtual));
    }

    @PutMapping("/{usuarioId}/senha")
    public UsuarioDTO atualizarSenha
            (@RequestBody UsuarioSenhaDTO usuarioSenhaDTO, @PathVariable("usuarioId") Long id){

        var senhaAtual = usuarioSenhaDTO.getSenhaAtual();
        var novaSenha = usuarioSenhaDTO.getNovaSenha();

        System.out.println("atual " +senhaAtual + "nova senha " + novaSenha);
        var usuarioAtual = usuarioService.atualizarSenha(id, senhaAtual, novaSenha);
        return usuarioAssembler.toDTO(usuarioAtual);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long id){
        usuarioService.remover(id);
    }

}
