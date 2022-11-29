package com.algaworks.algafood.domain.model.exception;

public class ProdutoNaoEncontratoException extends EntidadeNaoEncontradaException{


    private static final long serialVersionUID = 5445640789113119018L;

    public ProdutoNaoEncontratoException(String mensagem) {
        super(mensagem);
    }

    public ProdutoNaoEncontratoException(Long id) {
        this(String.format("Produto com id %d não encontrado",id));
    }
}
