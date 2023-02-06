package com.algaworks.algafood.domain.model.exception;

public class SenhaAtualIncorretaException extends NegocioException{

    public SenhaAtualIncorretaException(String mensagem) {
        super(mensagem);
    }
}
