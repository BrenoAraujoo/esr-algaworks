package com.algaworks.algafood.domain.model.exception;

import java.io.Serial;

public class UsuarioNaoEncontradoException extends EntidadeNaoEncontradaException{
    @Serial
    private static final long serialVersionUID = -6847523989131916815L;

    public UsuarioNaoEncontradoException(String mensagem) {
        super(mensagem);
    }
    
    public UsuarioNaoEncontradoException (Long id){
        this(String.format("Não existe usuário com id %d ", id));
    }
}
