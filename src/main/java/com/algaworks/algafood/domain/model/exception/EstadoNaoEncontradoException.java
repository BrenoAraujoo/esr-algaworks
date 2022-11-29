package com.algaworks.algafood.domain.model.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


public class EstadoNaoEncontradoException extends EntidadeNaoEncontradaException{


    private static final long serialVersionUID = 5756116850212840039L;

    public EstadoNaoEncontradoException(String mensagem) {
        super(mensagem);
    }
    public EstadoNaoEncontradoException(Long id) {
        this(String.format("Não existe um cadastro de estado com id %d",id));
    }



}
