package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.model.Produto;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.model.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.model.exception.ProdutoNaoEncontratoException;
import com.algaworks.algafood.domain.model.exception.RestauranteNaoEncontradoException;
import com.algaworks.algafood.domain.model.repository.ProdutoRespository;
import com.algaworks.algafood.domain.model.repository.RestauranteRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CadastroProdutoService {

    @Autowired
    private ProdutoRespository produtoRespository;
    @Autowired
    private RestauranteRepository restauranteRepository;

    @Transactional
    public Produto salvar(Produto produto){
        var restauranteId = produto.getRestaurante().getId();

        Restaurante restaurante = restauranteRepository.findById(restauranteId)
                .orElseThrow(() -> new RestauranteNaoEncontradoException(restauranteId));

        BeanUtils.copyProperties(restaurante,produto.getRestaurante());
        return produtoRespository.save(produto);
    }

    public void remover(Long id){
        var produto = produtoRespository.findById(id);
        try {
            produtoRespository.deleteById(id);
            produtoRespository.flush();
        }catch (DataIntegrityViolationException e){
            throw  new EntidadeEmUsoException(
                    String.format("Produto com id %d em uso!",id)
            );
        }catch (EmptyResultDataAccessException e ){
            throw new ProdutoNaoEncontratoException(id);
        }
    }

    public Produto buscarOuFalhar(Long id){
        return produtoRespository.findById(id)
                .orElseThrow(()-> new ProdutoNaoEncontratoException(id));
    }
}
