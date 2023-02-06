package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.model.Usuario;
import com.algaworks.algafood.domain.model.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.model.exception.SenhaAtualIncorretaException;
import com.algaworks.algafood.domain.model.exception.UsuarioNaoEncontradoException;
import com.algaworks.algafood.domain.model.repository.UsuarioRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

@Service
public class CadastroUsuarioService {

    private final String SENHA_ATUAL_INCORRETA = "A senha atual informada não condiz com" +
            "a senha cadastrada. Por favor, verifique e tente novamente";
    private final String USUARIO_EM_USO = "O usuario com id % está em uso, logo não pode ser removido";
    @Autowired
    private UsuarioRepository usuarioRepository;


    public Usuario buscar(Long id){
        return buscarOuFalhar(id);
    }
    public List<Usuario> listar(){
        return usuarioRepository.findAll();
    }

    public Usuario salvar(Usuario usuario){
        return usuarioRepository.save(usuario);
    }

    public Usuario atualizarSenha(Long id, String senhaAtual, String novaSenha){
        var usuario = buscarOuFalhar(id);
        if(novaSenha != senhaAtual && senhaAtual.equals(usuario.getSenha())){
            usuario.setSenha(novaSenha);
            return salvar(usuario);
        }throw new SenhaAtualIncorretaException(SENHA_ATUAL_INCORRETA);

    }

    public void remover(Long id){
        try {
            usuarioRepository.deleteById(id);
        }catch (EmptyResultDataAccessException e){
            throw new UsuarioNaoEncontradoException(id);
        }catch (DataIntegrityViolationException e){
           throw new EntidadeEmUsoException(String.format(USUARIO_EM_USO,id));
        }
    }
    public Usuario buscarOuFalhar(Long id){
        return usuarioRepository.findById(id)
                .orElseThrow(()-> new UsuarioNaoEncontradoException(id));
    }
}
