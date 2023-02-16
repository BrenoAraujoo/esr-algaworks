package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.model.Grupo;
import com.algaworks.algafood.domain.model.Permissao;
import com.algaworks.algafood.domain.model.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.model.exception.GrupoNaoEncontradoException;
import com.algaworks.algafood.domain.model.repository.GrupoRepository;
import com.algaworks.algafood.domain.model.repository.PermissaoRepository;
import javax.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CadastroGrupoService {

    public final String MSG_GRUPO_EM_USO = "Grupo com id %d está em uso e não pode ser removido.";

    @Autowired
    private GrupoRepository grupoRepository;


    @Autowired
    private PermissaoRepository permissaoRepository;
    @Autowired
    private CadastroPermissaoService permissaoService;
    public Grupo buscarOufalhar(Long id){
        return grupoRepository.findById(id).orElseThrow(() -> new GrupoNaoEncontradoException(id));
    }

    @Transactional
    public Grupo salvar(Grupo grupo){
        return grupoRepository.save(grupo);
    }

    @Transactional
    public void remover(Long id){
        try {
            grupoRepository.deleteById(id);
            grupoRepository.flush();
        }catch (DataIntegrityViolationException e){
            throw new EntidadeEmUsoException(String.format(MSG_GRUPO_EM_USO,id));
        }catch (EmptyResultDataAccessException e){
            throw new GrupoNaoEncontradoException(id);
        }
    }

    @Transactional
    public void associarPermissao(Long grupoId, Long permissaoId){
        var grupo = buscarOufalhar(grupoId);
        var permissao = permissaoService.buscarOuFalhar(permissaoId);
        grupo.adicionarPermissao(permissao);
    }
    @Transactional
    public void desaassociarPermissao(Long grupoId, Long permissaoId){
        var grupo = buscarOufalhar(grupoId);
        var permissao = permissaoService.buscarOuFalhar(permissaoId);
        grupo.removerPermissao(permissao);
    }
}
