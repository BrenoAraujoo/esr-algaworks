package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.model.Produto;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.model.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.model.exception.ProdutoNaoEncontratoException;
import com.algaworks.algafood.domain.model.exception.RestauranteNaoEncontradoException;
import com.algaworks.algafood.domain.model.repository.ProdutoRespository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CadastroProdutoService {

    private final String PRODUTO_NÃO_ENCONTRADO = "Não existe produto com id %d cadastrado";

    @Autowired
    private ProdutoRespository produtoRespository;
    @Autowired
    private CadastroRestauranteService restauranteService;

    @Transactional
    public Produto salvar(Produto produto){
        return produtoRespository.save(produto);
    }

    @Transactional
    public void remover(Long produtoId){
        var produto = produtoRespository.findById(produtoId);
        try {
            produtoRespository.deleteById(produtoId);
            produtoRespository.flush();
        }catch (DataIntegrityViolationException e){
            throw  new EntidadeEmUsoException(
                    String.format("Produto com id %d em uso!",produtoId)
            );
        }catch (EmptyResultDataAccessException e ){
            throw new ProdutoNaoEncontratoException(String.format(PRODUTO_NÃO_ENCONTRADO,produtoId));
        }
    }

    @Transactional
    public void ativarProduto(Produto produto){
        produto.ativar();
    }
    @Transactional
    public void desativarProduto(Produto produto){
        produto.desativar();
    }

    public Produto buscarOuFalhar(Long produtoId, Long restauranteId){
        return produtoRespository.findById(produtoId, restauranteId)
                .orElseThrow(()-> new ProdutoNaoEncontratoException(produtoId, restauranteId));
    }
}
