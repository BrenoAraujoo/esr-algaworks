package com.algaworks.algafood.domain.model.exception;

public class PedidoNaoEncontradaException extends EntidadeNaoEncontradaException{

    private static final long serialVersionUID = -5652383170055662493L;

    public PedidoNaoEncontradaException(String codigo) {
        super(String.format("Pedido com código %s não encontrado",codigo));
    }

}
