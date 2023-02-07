package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.model.Usuario;
import com.algaworks.algafood.domain.model.exception.EmailJaCadastradoException;
import com.algaworks.algafood.domain.model.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.model.exception.NegocioException;
import com.algaworks.algafood.domain.model.exception.UsuarioNaoEncontradoException;
import com.algaworks.algafood.domain.model.repository.UsuarioRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CadastroUsuarioService {


    private final String SENHA_ATUAL_INCORRETA = "A senha atual informada não condiz com" +
            "a senha cadastrada. Por favor, verifique e tente novamente";
    private final String USUARIO_EM_USO = "O usuario com id % está em uso, logo não pode ser removido";

    private final String EMAIL_JA_CADASTRADO = "O e-mail %s já está em uso. Por favor, utilize outro e-mail";
    @Autowired
    private UsuarioRepository usuarioRepository;


    public Usuario buscar(Long id) {
        return buscarOuFalhar(id);
    }

    public List<Usuario> listar() {
        return usuarioRepository.findAll();
    }

    @Transactional
    public Usuario salvar(Usuario usuario) {

        usuarioRepository.detach(usuario);
        var usuarioExistente = usuarioRepository.findByEmail(usuario.getEmail());
            /*
            Regra de negocio: Não podemos ter um email para mais de um usuário
            Verificamos se o usuário existe - findByEmail
            e se existir, o usuário que estamos salvando deve ser diferente do usuário existente.
             */
        if (usuarioExistente.isPresent() && !usuarioExistente.get().equals(usuario)) {
            throw new EmailJaCadastradoException
                    (String.format(EMAIL_JA_CADASTRADO, usuarioExistente.get().getEmail()));
        }

        return usuarioRepository.save(usuario);
    }


    //Minha implementação
//    public Usuario atualizarSenha(Long id, String senhaAtual, String novaSenha){
//        var usuario = buscarOuFalhar(id);
//        if(novaSenha != senhaAtual && senhaAtual.equals(usuario.getSenha())){
//            usuario.setSenha(novaSenha);
//            return salvar(usuario);
//        }throw new SenhaAtualIncorretaException(SENHA_ATUAL_INCORRETA);
//
//    }

    @Transactional
    public void atualizarSenha(Long id, String senhaAtual, String novaSenha) {
        var usuario = buscarOuFalhar(id);
        if (usuario.senhaNaoCoincideCom(senhaAtual))
            throw new NegocioException(SENHA_ATUAL_INCORRETA);
        usuario.setSenha(novaSenha);

    }


    public void remover(Long id) {
        try {
            usuarioRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new UsuarioNaoEncontradoException(id);
        } catch (DataIntegrityViolationException e) {
            throw new EntidadeEmUsoException(String.format(USUARIO_EM_USO, id));
        }
    }

    public Usuario buscarOuFalhar(Long id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new UsuarioNaoEncontradoException(id));
    }
}
