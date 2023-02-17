package com.algaworks.algafood.domain.model.exception;

public class PedidoNaoEncontradaException extends EntidadeNaoEncontradaException{


    private static final long serialVersionUID = -5652383170055662493L;

    public PedidoNaoEncontradaException(String mensagem) {
        super(mensagem);
    }

    public PedidoNaoEncontradaException(Long id) {
        this(String.format("Pedido com id %d não encontrado",id));
    }
}
