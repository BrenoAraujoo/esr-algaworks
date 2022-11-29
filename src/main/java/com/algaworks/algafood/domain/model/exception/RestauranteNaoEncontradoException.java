package com.algaworks.algafood.domain.model.exception;


public class RestauranteNaoEncontradoException extends EntidadeNaoEncontradaException{


    private static final long serialVersionUID = -8897157257706225852L;

    public RestauranteNaoEncontradoException(String mensagem) {
        super(mensagem);
    }

    public RestauranteNaoEncontradoException(Long id) {
        this(String.format("Restaurante com id %d não encontrado",id));
    }
}
