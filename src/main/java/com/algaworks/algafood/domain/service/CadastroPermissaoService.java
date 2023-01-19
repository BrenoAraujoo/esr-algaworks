package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.model.Permissao;
import com.algaworks.algafood.domain.model.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.model.exception.PermissaoNaoEncontradaException;
import com.algaworks.algafood.domain.model.repository.PermissaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CadastroPermissaoService {

    public static final String MSG_PERMISSAO_EM_USO = "Permissao com id %d em uso";
    @Autowired
    private PermissaoRepository permissaoRepository;

    @Transactional
    public Permissao salvar(Permissao permissao) {
        return permissaoRepository.save(permissao);
    }

    @Transactional
    public void remover(Long id) {
        try {
            permissaoRepository.deleteById(id);
            permissaoRepository.flush();
        } catch (DataIntegrityViolationException e) {
            throw new EntidadeEmUsoException(
                    String.format(MSG_PERMISSAO_EM_USO, id)
            );
        } catch (EmptyResultDataAccessException e) {
            throw new PermissaoNaoEncontradaException(id);
        }
    }
    public Permissao buscarOuFalhar(Long id){
        return permissaoRepository.findById(id).orElseThrow(()-> new PermissaoNaoEncontradaException(id));
    }
}
