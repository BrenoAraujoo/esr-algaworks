package com.algaworks.algafood.domain.model.exception;

public class ProdutoNaoEncontratoException extends EntidadeNaoEncontradaException{


    private static final long serialVersionUID = 5445640789113119018L;

    public ProdutoNaoEncontratoException(String mensagem) {
        super(mensagem);
    }

    public ProdutoNaoEncontratoException(Long produtoId, Long restauranteId) {
        this(String.format("Não existe um produto com id %d vinculado ao restaurante de id %d",produtoId, restauranteId));
    }
}
