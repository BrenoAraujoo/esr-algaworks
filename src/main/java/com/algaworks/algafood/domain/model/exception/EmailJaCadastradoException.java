package com.algaworks.algafood.domain.model.exception;

public class EmailJaCadastradoException extends NegocioException{


    private static final long serialVersionUID = -8317392310619229659L;

    public EmailJaCadastradoException(String mensagem) {
        super(mensagem);
    }
}
