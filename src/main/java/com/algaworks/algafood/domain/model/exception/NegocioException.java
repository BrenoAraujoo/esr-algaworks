package com.algaworks.algafood.domain.model.exception;

public class NegocioException extends RuntimeException{


    private static final long serialVersionUID = 5888233874011274070L;

    public NegocioException(String mensagem) {
        super(mensagem);
    }

    public NegocioException(String mensagem, Throwable causa) {
        super(mensagem,causa);
    }
}
