package com.algaworks.algafood.domain.model.exception;

public class CidadeNaoEncontradaException extends EntidadeNaoEncontradaException{


    private static final long serialVersionUID = -5652383170055662493L;

    public CidadeNaoEncontradaException(String mensagem) {
        super(mensagem);
    }

    public CidadeNaoEncontradaException(Long id) {
        this(String.format("Cidade com id %d não encontrada",id));
    }
}
