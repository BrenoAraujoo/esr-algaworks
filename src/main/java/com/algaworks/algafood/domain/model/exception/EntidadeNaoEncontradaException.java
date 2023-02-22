package com.algaworks.algafood.domain.model.exception;

public  abstract class EntidadeNaoEncontradaException extends NegocioException{


    private static final long serialVersionUID = -9110157576385254083L;

    public EntidadeNaoEncontradaException(String mensagem) {
        super(mensagem);
    }
}
