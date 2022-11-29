package com.algaworks.algafood.domain.model.exception;


public class PermissaoNaoEncontradaException extends EntidadeNaoEncontradaException{


    private static final long serialVersionUID = 199675500233481797L;

    public PermissaoNaoEncontradaException(String mensagem) {
        super(mensagem);
    }

    public PermissaoNaoEncontradaException(Long id) {
        this(String.format("Permissao com id %d não encontrada",id));
    }
}
