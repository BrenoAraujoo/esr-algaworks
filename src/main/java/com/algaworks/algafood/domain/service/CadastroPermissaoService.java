package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.model.Permissao;
import com.algaworks.algafood.domain.model.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.model.exception.PermissaoNaoEncontradaException;
import com.algaworks.algafood.domain.model.repository.PermissaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

@Service
public class CadastroPermissaoService {

    public static final String MSG_PERMISSAO_EM_USO = "Permissao com id %d em uso";
    @Autowired
    private PermissaoRepository permissaoRepository;

    public Permissao salvar(Permissao permissao) {
        return permissaoRepository.save(permissao);
    }

    public void remover(Long id) {
        try {
            permissaoRepository.deleteById(id);

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
