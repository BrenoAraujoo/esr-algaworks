package com.algaworks.algafood.domain.model.exception;

public class GrupoNaoEncontradoException extends EntidadeNaoEncontradaException{


    private static final long serialVersionUID = 7636918665648503785L;

    public GrupoNaoEncontradoException(String mensagem) {
        super(mensagem);
    }

    public GrupoNaoEncontradoException(Long id) {
        this(String.format("Grupo com id %d não encontrado",id));
    }
}
