package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.model.exception.CozinhaNaoEncontradaException;
import com.algaworks.algafood.domain.model.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.model.repository.CozinhaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CadastroCozinhaService {
    private static final String MSG_COZINHA_EM_USO = "Cozinha com id %d não pode ser removida pois está em uso";
    @Autowired
    private CozinhaRepository cozinhaRepository;

    @Transactional
    public Cozinha salvar(Cozinha cozinha) {
        return cozinhaRepository.save(cozinha);
    }
    @Transactional
    public void remover(Long id) {
        try {
            cozinhaRepository.deleteById(id);
            cozinhaRepository.flush();
        } catch (DataIntegrityViolationException e) {
            throw new EntidadeEmUsoException(
                    String.format(MSG_COZINHA_EM_USO,id)
            );
        }
        catch (EmptyResultDataAccessException e){
            throw new CozinhaNaoEncontradaException(id);
        }
    }

    public Cozinha buscarOuFalhar(Long id){
        return cozinhaRepository.findById(id).orElseThrow(()->new CozinhaNaoEncontradaException(id));
    }
}
