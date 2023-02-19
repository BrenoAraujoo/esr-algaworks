package com.algaworks.algafood.api.controller;


import com.algaworks.algafood.api.assembler.usuario.UsuarioAssembler;
import com.algaworks.algafood.api.assembler.usuario.UsuarioDisassembler;
import com.algaworks.algafood.api.model.dto.UsuarioDTO;
import com.algaworks.algafood.api.model.dtoinput.UsuarioAtualizarInput;
import com.algaworks.algafood.api.model.dtoinput.UsuarioInput;
import com.algaworks.algafood.api.model.dtoinput.Senha;
import com.algaworks.algafood.domain.model.Usuario;
import com.algaworks.algafood.domain.model.repository.UsuarioRepository;
import com.algaworks.algafood.domain.service.CadastroUsuarioService;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/usuarios",
        produces = MediaType.APPLICATION_JSON_VALUE)
public class UsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private CadastroUsuarioService usuarioService;

    @Autowired
    private UsuarioAssembler usuarioAssembler;

    @Autowired
    private UsuarioDisassembler usuarioDisassembler;

    @GetMapping
    public List<UsuarioDTO> listar(){
        List<Usuario> usuarios = usuarioService.listar();
        return usuarioAssembler.toCollectionDTO(usuarios);
    }

    @GetMapping("/{usuarioId}")
    public UsuarioDTO buscar(@PathVariable ("usuarioId") Long id){
        return usuarioAssembler.toDTO(usuarioService.buscarOuFalhar(id));
    }

    @PostMapping
    public UsuarioDTO salvar(@RequestBody @Valid UsuarioInput usuarioInput){

        var usuario = usuarioDisassembler.toDomainObject(usuarioInput);

        return usuarioAssembler.toDTO(usuarioService.salvar(usuario));
    }

    @PutMapping("/{usuarioId}")
    public UsuarioDTO atualizar
            (@RequestBody @Valid UsuarioAtualizarInput usuarioInput, @PathVariable("usuarioId") Long id){
        var usuarioAtual = usuarioService.buscarOuFalhar(id);
        usuarioDisassembler.copyFromInputToDomain(usuarioInput, usuarioAtual);
        return usuarioAssembler.toDTO(usuarioService.salvar(usuarioAtual));
    }

    @PutMapping("/{usuarioId}/senha")
    public void atualizarSenha (@RequestBody @Valid Senha senha, @PathVariable("usuarioId") Long id){
        usuarioService.atualizarSenha(id, senha.getSenhaAtual(), senha.getNovaSenha());
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long id){
        usuarioService.remover(id);
    }

}
