package com.algaworks.algafood.domain.model.exception;

public  abstract class EntidadeNaoEncontradaException extends NegocioException{

    private static final long serialVersionUID = 7428130923099171485L;

    public EntidadeNaoEncontradaException(String mensagem) {
        super(mensagem);
    }
}
