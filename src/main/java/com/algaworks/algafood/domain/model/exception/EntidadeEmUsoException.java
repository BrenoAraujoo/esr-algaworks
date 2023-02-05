package com.algaworks.algafood.domain.model.exception;

public class EntidadeEmUsoException extends RuntimeException{

    private static final long serialVersionUID = 676367904201731613L;

    public EntidadeEmUsoException(String mensagem) {
        super(mensagem);
    }

}
